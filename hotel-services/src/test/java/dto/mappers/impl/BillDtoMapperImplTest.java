package dto.mappers.impl;

import by.khadasevich.hotel.dto.BillDto;
import by.khadasevich.hotel.dto.mappers.BillDtoMapper;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.services.BillService;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.entities.enums.BillStatusType;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.math.BigDecimal;
import java.sql.Date;

public class BillDtoMapperImplTest extends Assert {
    private BillService billService = SingletonBuilder.getInstanceImpl(BillService.class);
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);
    private BillDtoMapper billDtoMapper = SingletonBuilder.getInstanceImpl(BillDtoMapper.class);

    @Test
    public void map(){

        Bill getIt;
        Bill newOneBill = new Bill();
        newOneBill.setDate(Date.valueOf("2017-08-24"));
//        newOne.setOrderId(1L);
        newOneBill.setUserId(1L);
        newOneBill.setRoomId(1L);
        newOneBill.setArrivalDate(Date.valueOf("1980-01-01"));
        newOneBill.setEventsDate(Date.valueOf("1988-01-10"));
        newOneBill.setTotal(1000L);
        newOneBill.setStatus(BillStatusType.UNPAID);

        Order newOneOrder = new Order();
        newOneOrder.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneOrder.setAdminId(1L);
        newOneOrder.setStatus(OrderStatusType.APPROVED);
        newOneOrder.setUserId(1L);
        newOneOrder.setRoomTypeId(1L);
        newOneOrder.setRoomId(1L);
        newOneOrder.setArrivalDate(Date.valueOf("1980-01-01"));
        newOneOrder.setEventsDate(Date.valueOf("1988-01-10"));
        newOneOrder.setTotal(1000L);

        //save order in DB
        newOneOrder = orderService.save(newOneOrder, newOneOrder.getUserId());
        newOneBill.setOrderId(newOneOrder.getId());
        //save bill in Db
        Bill savedBill = billService.save(newOneBill);
        //map billDto
        BillDto billDto = billDtoMapper.map(savedBill);
        //check valid map
        assertEquals(billDto.getId(), savedBill.getId());
        assertEquals(billDto.getOrderId(), savedBill.getOrderId());
        assertEquals(billDto.getUserId(), savedBill.getUserId());
        assertEquals(billDto.getGuestName(), "Maikle Jordan");
        assertEquals(billDto.getHotelId(), 1L); //hotelId take from roomType
        assertEquals(billDto.getHotelName(), "Negresko Princes");
        assertEquals(billDto.getRoomId(), savedBill.getRoomId());
        assertEquals(billDto.getRoomTypeName(), "ONE");
        assertEquals(billDto.getRoomNumber(), "101");
        assertEquals(billDto.getArrivalDate(), savedBill.getArrivalDate());
        assertEquals(billDto.getEventsDate(), savedBill.getEventsDate());
        BigDecimal price = new BigDecimal(String.valueOf("3000"));
        price = price.divide(BigDecimal.TEN.multiply(BigDecimal.TEN),2, BigDecimal.ROUND_HALF_UP);
        assertEquals(billDto.getPrice(), price);
        BigDecimal total = new BigDecimal(String.valueOf(savedBill.getTotal()));
        total = total.divide(BigDecimal.TEN.multiply(BigDecimal.TEN),2, BigDecimal.ROUND_HALF_UP);
        assertEquals(billDto.getTotal(), total);
        assertEquals(billDto.getCurrency(), "USD");
        assertEquals(billDto.getStatus(), savedBill.getStatus());

        //delete bill from DB
        int deletedNumber = billService.delete(savedBill.getId());
        assertEquals(deletedNumber, 1);
        getIt = billService.get(savedBill.getId());
        assertNull(getIt);

        //delete order from BD
        deletedNumber = orderService.delete(newOneOrder.getId());
        assertEquals(deletedNumber, 1);
        newOneOrder = orderService.get(newOneOrder.getId());
        assertNull(newOneOrder);

    }
}
