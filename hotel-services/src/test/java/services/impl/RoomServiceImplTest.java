package services.impl;

import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.Room;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.services.RoomService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.Date;
import java.util.List;

public class RoomServiceImplTest extends Assert {
    private RoomService roomService = SingletonBuilder.getInstanceImpl(RoomService.class);
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);

    @Test
    public void get() {
        Room getIt = roomService.get(1L);

        assertEquals("101", getIt.getNumber());
        assertEquals(1L, getIt.getRoomTypeId());
        assertNotNull(getIt);

    }

    @Test
    public void getAllFreeForRoomType() {
        long[] orderIdArray = new long[4];

        Order newOneForSave = new Order();
        newOneForSave.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneForSave.setAdminId(1L);
        newOneForSave.setUserId(3L);
        newOneForSave.setRoomTypeId(1L);
        newOneForSave.setRoomId(1L);

        newOneForSave.setStatus(OrderStatusType.APPROVED);
        newOneForSave.setRoomId(1L);
        newOneForSave.setArrivalDate(Date.valueOf("1980-01-01"));
        newOneForSave.setEventsDate(Date.valueOf("1980-01-10"));
        newOneForSave = orderService.save(newOneForSave, 1L);
        orderIdArray[0] = newOneForSave.getId();

        newOneForSave.setStatus(OrderStatusType.APPROVED);
        newOneForSave.setRoomId(2L);
        newOneForSave.setArrivalDate(Date.valueOf("1980-01-05"));
        newOneForSave.setEventsDate(Date.valueOf("1980-01-15"));
        newOneForSave = orderService.save(newOneForSave, 1L);
        orderIdArray[1] = newOneForSave.getId();

        newOneForSave.setStatus(OrderStatusType.PAID);
        newOneForSave.setRoomId(3L);
        newOneForSave.setArrivalDate(Date.valueOf("1980-01-07"));
        newOneForSave.setEventsDate(Date.valueOf("1980-01-17"));
        newOneForSave = orderService.save(newOneForSave, 1L);
        orderIdArray[2] = newOneForSave.getId();

        newOneForSave.setStatus(OrderStatusType.NEW);
        newOneForSave.setRoomId(4L);
        newOneForSave.setArrivalDate(Date.valueOf("1980-01-07"));
        newOneForSave.setEventsDate(Date.valueOf("1980-01-17"));
        newOneForSave = orderService.save(newOneForSave, 1L);
        orderIdArray[3] = newOneForSave.getId();

        List<Room> freeRooms = roomService.getAllFreeForRoomType(1L,
                Date.valueOf("1980-01-07"), Date.valueOf("1980-01-27"));

        assertEquals(freeRooms.size(), 7);

        for (long orderId : orderIdArray) {
            orderService.delete(orderId);
        }
    }
}
