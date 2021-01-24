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
import java.sql.SQLException;
import java.util.List;

public class BillServiceImplTest extends Assert {
    private BillService billService = SingletonBuilder.getInstanceImpl(BillService.class);
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);

    @Test
    public void getSaveDelete() {
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        Bill saved;
        Bill getIt;
        Bill newOne = new Bill();
        newOne.setDate(Date.valueOf("2017-08-24"));
//        newOne.setOrderId(1L);
        newOne.setUserId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1980-01-01"));
        newOne.setEventsDate(Date.valueOf("1988-01-10"));
        newOne.setTotal(1000L);
        newOne.setStatus(BillStatusType.UNPAID);

        Order newOneOrder = new Order();
        newOneOrder.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneOrder.setAdminId(1L);
        newOneOrder.setStatus(OrderStatusType.APPROVED);
        newOneOrder.setUserId(3L);
        newOneOrder.setRoomTypeId(1L);
        newOneOrder.setRoomId(1L);
        newOneOrder.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneOrder.setEventsDate(Date.valueOf("1990-01-10"));
        newOneOrder.setTotal(1000L);

        newOneOrder = orderService.save(newOneOrder, newOneOrder.getUserId());
        newOne.setOrderId(newOneOrder.getId());

        long idBeforeSave = newOne.getId();
        saved = billService.save(newOne);
        assertNotEquals(saved.getId(), idBeforeSave);
        assertNotNull(saved);
        getIt = billService.get(saved.getId());

        assertEquals(saved, getIt);
        assertNotNull(getIt);

        int deletedNumber = billService.delete(getIt.getId());
        assertEquals(deletedNumber, 1);
        getIt = billService.get(saved.getId());
        assertNull(getIt);

        deletedNumber = orderService.delete(newOneOrder.getId());
        assertEquals(deletedNumber, 1);
        newOneOrder = orderService.get(newOneOrder.getId());
        assertNull(newOneOrder);

    }

    @Test
    public void billPay() throws SQLException {
        // bill del from DB
        assertFalse(billService.billPay(-1L));

        int deletedNumber;
        Bill saved;
        Bill getIt;
        Bill newOne = new Bill();
        newOne.setDate(Date.valueOf("2017-08-24"));
//        newOne.setOrderId(1L);
        newOne.setUserId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1980-01-01"));
        newOne.setEventsDate(Date.valueOf("1988-01-10"));
        newOne.setTotal(1000L);
        newOne.setStatus(BillStatusType.PAID);

        Order newOneOrder = new Order();
        newOneOrder.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneOrder.setAdminId(1L);
        newOneOrder.setStatus(OrderStatusType.APPROVED);
        newOneOrder.setUserId(3L);
        newOneOrder.setRoomTypeId(1L);
        newOneOrder.setRoomId(1L);
        newOneOrder.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneOrder.setEventsDate(Date.valueOf("1990-01-10"));
        newOneOrder.setTotal(1000L);

        newOneOrder = orderService.save(newOneOrder, newOneOrder.getUserId());
        newOne.setOrderId(newOneOrder.getId());

        saved = billService.save(newOne);
        // bill paid, nothing to do
        assertFalse(billService.billPay(saved.getId()));
        deletedNumber = billService.delete(saved.getId());
        assertEquals(deletedNumber, 1);
        getIt = billService.get(saved.getId());
        assertNull(getIt);

        // bill UNPAID, pay bill
        newOne.setOrderId(newOneOrder.getId());
        newOne.setStatus(BillStatusType.UNPAID);
        saved = billService.save(newOne);
        assertTrue(billService.billPay(saved.getId()));

        //bill change status
        getIt = billService.get(saved.getId());
        assertEquals(BillStatusType.PAID, getIt.getStatus());
        deletedNumber = billService.delete(saved.getId());
        assertEquals(deletedNumber, 1);
        getIt = billService.get(saved.getId());
        assertNull(getIt);

        //order change status
        newOneOrder = orderService.get(newOneOrder.getId());
        assertEquals(OrderStatusType.PAID, newOneOrder.getStatus());
        deletedNumber = orderService.delete(newOneOrder.getId());
        assertEquals(deletedNumber, 1);
        newOneOrder = orderService.get(newOneOrder.getId());
        assertNull(newOneOrder);

    }

    @Test
    public void getAllForUserId() throws SQLException {
        Bill saved;
        Bill newOne = new Bill();
        newOne.setDate(Date.valueOf("2017-08-24"));
//        newOne.setOrderId(1L);
        newOne.setUserId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1980-01-01"));
        newOne.setEventsDate(Date.valueOf("1988-01-10"));
        newOne.setTotal(1000L);
        newOne.setStatus(BillStatusType.PAID);

        Order newOneOrder = new Order();
        newOneOrder.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneOrder.setAdminId(1L);
        newOneOrder.setStatus(OrderStatusType.APPROVED);
        newOneOrder.setUserId(3L);
        newOneOrder.setRoomTypeId(1L);
        newOneOrder.setRoomId(1L);
        newOneOrder.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneOrder.setEventsDate(Date.valueOf("1990-01-10"));
        newOneOrder.setTotal(1000L);


        newOneOrder = orderService.save(newOneOrder, newOneOrder.getUserId());
        newOne.setOrderId(newOneOrder.getId());

        long[] deletedId = new long[2];
        List<Bill> billList1 = billService.getAllForUserId(1L);
        saved = billService.save(newOne);
        deletedId[0] = saved.getId();
        saved = billService.save(newOne);
        deletedId[1] = saved.getId();
        List<Bill> billList2 = billService.getAllForUserId(1L);
        assertEquals(billList2.size() - billList1.size(), 2);

        // del bills after check
        for (long id : deletedId) {
            billService.delete(id);
        }
        billList2 = billService.getAllForUserId(1L);
        assertEquals(billList2.size(), billList1.size());
        // del order after check
        int deletedNumber = orderService.delete(newOneOrder.getId());
        assertEquals(deletedNumber, 1);
        newOneOrder = orderService.get(newOneOrder.getId());
        assertNull(newOneOrder);

    }

    @Test
    public void getAllForBillStatus() throws SQLException {
        Bill saved;
        Bill newOne = new Bill();
        newOne.setDate(Date.valueOf("2017-08-24"));
//        newOne.setOrderId(1L);
        newOne.setUserId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1980-01-01"));
        newOne.setEventsDate(Date.valueOf("1988-01-10"));
        newOne.setTotal(1000L);
        newOne.setStatus(BillStatusType.PAID);

        Order newOneOrder = new Order();
        newOneOrder.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneOrder.setAdminId(1L);
        newOneOrder.setStatus(OrderStatusType.APPROVED);
        newOneOrder.setUserId(3L);
        newOneOrder.setRoomTypeId(1L);
        newOneOrder.setRoomId(1L);
        newOneOrder.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneOrder.setEventsDate(Date.valueOf("1990-01-10"));
        newOneOrder.setTotal(1000L);


        newOneOrder = orderService.save(newOneOrder, newOneOrder.getUserId());
        newOne.setOrderId(newOneOrder.getId());

        long[] deletedId = new long[2];
        List<Bill> billList1 = billService.getAllForBillStatus(BillStatusType.PAID);
        saved = billService.save(newOne);
        deletedId[0] = saved.getId();
        saved = billService.save(newOne);
        deletedId[1] = saved.getId();
        List<Bill> billList2 = billService.getAllForBillStatus(BillStatusType.PAID);
        assertEquals(billList2.size() - billList1.size(), 2);

        // del bills after check
        for (long id : deletedId) {
            billService.delete(id);
        }
        billList2 = billService.getAllForBillStatus(BillStatusType.PAID);
        assertEquals(billList2.size(), billList1.size());
        // del order after check
        int deletedNumber = orderService.delete(newOneOrder.getId());
        assertEquals(deletedNumber, 1);
        newOneOrder = orderService.get(newOneOrder.getId());
        assertNull(newOneOrder);
    }
}
