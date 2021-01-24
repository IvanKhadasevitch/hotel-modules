package services.impl;

import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.BillStatusType;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.services.BillService;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class OrderServiceImplTest extends Assert {
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);
    private BillService billService = SingletonBuilder.getInstanceImpl(BillService.class);

    @Test
    public void make() {
        Order order = orderService.make(1L, Date.valueOf("1980-01-01"),
                Date.valueOf("1988-01-10"));
        assertNotNull(order);
        assertEquals(order.getStatus(), OrderStatusType.NEW);
        assertEquals(order.getRoomTypeId(), 1L);
        assertEquals(order.getArrivalDate(), Date.valueOf("1980-01-01"));
        assertEquals(order.getEventsDate(), Date.valueOf("1988-01-10"));
    }

    @Test
    public void getSaveDelete() {
        Order saved;
        Order getIt;

        Order newOne = new Order();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOne.setDateProcessing(Date.valueOf("1990-01-01"));
        newOne.setAdminId(1L);
        newOne.setStatus(OrderStatusType.APPROVED);
        newOne.setUserId(3L);
        newOne.setRoomTypeId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1990-01-01"));
        newOne.setEventsDate(Date.valueOf("1990-01-10"));
        newOne.setTotal(1000L);

        long idBeforeSave = newOne.getId();
        saved = orderService.save(newOne, newOne.getUserId());
        assertNotEquals(saved.getId(), idBeforeSave);
        assertNotNull(saved);
        getIt = orderService.get(saved.getId());

        assertEquals(saved, getIt);
        assertNotNull(getIt);

        int deletedNumber = orderService.delete(getIt.getId());
        assertEquals(deletedNumber, 1);
        getIt = orderService.get(saved.getId());
        assertNull(getIt);

    }

    @Test
    public void decline() {
        // no order in the database
        assertFalse(orderService.decline(-1L,1L));

        //make APPROVED order
        Order saved;
        Order getIt;
        Order newOne = new Order();
        newOne.setDateProcessing(Date.valueOf("1990-01-01"));
        newOne.setAdminId(1L);
        newOne.setStatus(OrderStatusType.APPROVED);
        newOne.setUserId(3L);
        newOne.setRoomTypeId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1990-01-01"));
        newOne.setEventsDate(Date.valueOf("1990-01-10"));
        newOne.setTotal(1000L);

        //save order in DB with status APPROVED
        saved = orderService.save(newOne, newOne.getUserId());
        // order with status APPROVED, can't approve
        assertFalse(orderService.decline(saved.getId(),newOne.getAdminId()));
        //del order from DB
        int deletedNumber = orderService.delete(saved.getId());
        assertEquals(deletedNumber, 1);
        getIt = orderService.get(saved.getId());
        assertNull(getIt);

        //save order in DB with status NEW
        newOne.setStatus(OrderStatusType.NEW);
        saved = orderService.save(newOne, 2L);
        //decline order
        assertTrue(orderService.decline(saved.getId(), 2L));
        saved = orderService.get(saved.getId());
        //check change order.adminId
        assertNotEquals(newOne.getAdminId(), saved.getAdminId());
        assertEquals(saved.getAdminId(), 2L);
        //check change order.OrderStatus
        assertNotEquals(newOne.getStatus(), saved.getStatus());
        assertEquals(saved.getStatus(), OrderStatusType.DECLINE);

        //del order from DB
        deletedNumber = orderService.delete(saved.getId());
        assertEquals(deletedNumber, 1);
        getIt = orderService.get(saved.getId());
        assertNull(getIt);
    }

    @Test
    public void approve() {
        // no order in the database
        assertNull(orderService.approve(-1L,1L));

        //make NEW order
        Order saved;
        Order getIt;
        Order newOne = new Order();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOne.setDateProcessing(Date.valueOf("1990-01-02"));
        newOne.setAdminId(1L);
        newOne.setStatus(OrderStatusType.NEW);
        newOne.setUserId(3L);
        newOne.setRoomTypeId(1L);
//        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1990-01-01"));
        newOne.setEventsDate(Date.valueOf("1990-01-10"));
        newOne.setTotal(1000L);

        //save order in DB with status NEW & ArrivalDate less then current Date
        saved = orderService.save(newOne, newOne.getUserId());
        // expired Order can't approve
        assertNull(orderService.approve(saved.getId(), saved.getAdminId()));
        //del order from DB
        int deletedNumber = orderService.delete(saved.getId());
        assertEquals(deletedNumber, 1);
        getIt = orderService.get(saved.getId());
        assertNull(getIt);


        //make NEW order not expired
        Date currentDate = new Date((new java.util.Date()).getTime());
        Date arrivalDate = Date.valueOf(currentDate.toLocalDate().plusDays(1));
        Date eventsDate = Date.valueOf(currentDate.toLocalDate().plusDays(2));
        assertEquals(arrivalDate.toLocalDate().until(eventsDate.toLocalDate(), ChronoUnit.DAYS),
                1);
        newOne.setDateProcessing(currentDate);
        newOne.setArrivalDate(arrivalDate);
        newOne.setEventsDate(eventsDate);
        //save order in DB
        saved = orderService.save(newOne, newOne.getUserId());
        // approve order & make bill
        Bill bill = orderService.approve(saved.getId(), saved.getAdminId());
        assertNotNull(bill);
        Bill billFromDB = billService.get(bill.getId());
        assertEquals(bill.getId(),billFromDB.getId());
        assertEquals(bill.getStatus(),billFromDB.getStatus());
        assertEquals(bill.getArrivalDate(),billFromDB.getArrivalDate());
        assertEquals(bill.getEventsDate(),billFromDB.getEventsDate());
        assertEquals(bill.getTotal(),billFromDB.getTotal());
        assertEquals(bill.getStatus(), BillStatusType.UNPAID);
        //check order changes
        getIt = orderService.get(saved.getId());
        assertEquals(getIt.getStatus(), OrderStatusType.APPROVED);
        assertEquals(getIt.getBillId(), billFromDB.getId());
        assertNotEquals(getIt.getRoomId(), 0L);

        //del bill from DB
        deletedNumber = billService.delete(billFromDB.getId());
        assertEquals(deletedNumber, 1);
        billFromDB = billService.get(billFromDB.getId());
        assertNull(billFromDB);

        //del order from DB
        deletedNumber = orderService.delete(saved.getId());
        assertEquals(deletedNumber, 1);
        getIt = orderService.get(saved.getId());
        assertNull(getIt);
    }

    @Test
    public void getAllForUserId(){
        //make NEW order
        Order saved;
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

        List<Order> orderList1 = orderService.getAllForUserId(1L);
        //save order in DB
        long[] orderSavedId = new long[2];
        saved = orderService.save(newOne, newOne.getUserId());
        orderSavedId[0] = saved.getId();
        saved = orderService.save(newOne, newOne.getUserId());
        orderSavedId[1] = saved.getId();
        List<Order> orderList2 = orderService.getAllForUserId(1L);
        assertEquals(orderList2.size() - orderList1.size(), 2);

        //del orders from DB
        for (long id :orderSavedId) {
            orderService.delete(id);
        }
        orderList2 = orderService.getAllForUserId(1L);
        assertEquals(orderList2.size(), orderList1.size());
    }

    @Test
    public void getAllForStatus(){
        //make NEW order
        Order saved;
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

        List<Order> orderList1 = orderService.getAllForStatus(OrderStatusType.NEW);
        //save order in DB
        long[] orderSavedId = new long[2];
        saved = orderService.save(newOne, newOne.getUserId());
        orderSavedId[0] = saved.getId();
        saved = orderService.save(newOne, newOne.getUserId());
        orderSavedId[1] = saved.getId();
        List<Order> orderList2 = orderService.getAllForStatus(OrderStatusType.NEW);
        assertEquals(orderList2.size() - orderList1.size(), 2);

        //del orders from DB
        for (long id :orderSavedId) {
            orderService.delete(id);
        }
        orderList2 = orderService.getAllForStatus(OrderStatusType.NEW);
        assertEquals(orderList2.size(), orderList1.size());
    }
}

