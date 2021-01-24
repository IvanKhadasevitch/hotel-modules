package dto.mappers.impl;

import by.khadasevich.hotel.dto.OrderDto;
import by.khadasevich.hotel.dto.mappers.OrderDtoMapper;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.math.BigDecimal;
import java.sql.Date;

public class OrderDtoMapperImplTest extends Assert {
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);
    private OrderDtoMapper orderDtoMapper = SingletonBuilder.getInstanceImpl(OrderDtoMapper.class);


    @Test
    public void map() {
        //make NEW order
        Order savedOrder;
        Order getIt;
        Order newOne = new Order();
        newOne.setDateProcessing(Date.valueOf("1990-01-02"));
        newOne.setAdminId(1L);
        newOne.setStatus(OrderStatusType.NEW);
        newOne.setUserId(1L);
        newOne.setRoomTypeId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1990-01-01"));
        newOne.setEventsDate(Date.valueOf("1990-01-10"));
        newOne.setTotal(1000L);

        //save order in DB with status NEW & ArrivalDate less then current Date
        savedOrder = orderService.save(newOne, newOne.getUserId());

        //make OrderDto
        OrderDto orderDto = orderDtoMapper.map(savedOrder);

        //check valid mapping
        assertEquals(orderDto.getId(), savedOrder.getId());
        assertEquals(orderDto.getDateProcessing(), savedOrder.getDateProcessing());
        assertEquals(orderDto.getHotelId(), 1L);    //hotelId take from roomType
        assertEquals(orderDto.getHotelName(), "Negresko Princes");//hotelName take from roomType
        assertEquals(orderDto.getRoomTypeName(), "ONE");
        assertEquals(orderDto.getRoomNumber(), "101");
        assertEquals(orderDto.getSeats(), 1);
        BigDecimal price = new BigDecimal(String.valueOf("3000"));
        price = price.divide(BigDecimal.TEN.multiply(BigDecimal.TEN),2, BigDecimal.ROUND_HALF_UP);
        assertEquals(orderDto.getPrice(), price);
        assertEquals(orderDto.getGuestName(), "Maikle Jordan");
        assertEquals(orderDto.getArrivalDate(), savedOrder.getArrivalDate());
        assertEquals(orderDto.getEventsDate(), savedOrder.getEventsDate());
        assertTrue(orderDto.isExpired());
        assertEquals(orderDto.getStatus(), savedOrder.getStatus());
        assertEquals(orderDto.getCurrency(), "USD");

        //delete order from DB
        int deletedNumber = orderService.delete(savedOrder.getId());
        assertEquals(deletedNumber, 1);
        getIt = orderService.get(savedOrder.getId());
        assertNull(getIt);
    }
}
