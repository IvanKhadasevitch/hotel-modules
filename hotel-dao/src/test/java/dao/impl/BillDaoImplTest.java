package dao.impl;

import by.khadasevich.hotel.dao.BillDao;
import by.khadasevich.hotel.dao.OrderDao;
import by.khadasevich.hotel.db.ConnectionManager;

import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;
import by.khadasevich.hotel.entities.enums.BillStatusType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class BillDaoImplTest extends Assert {
    private BillDao dao = SingletonBuilder.getInstanceImpl(BillDao.class);
    private OrderDao orderDao = SingletonBuilder.getInstanceImpl(OrderDao.class);


    @Test
    public void saveGetDelete() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);
        Bill saved;
        Bill getIt;
        Bill newOne = new Bill();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOne.setDate(Date.valueOf("2017-08-24"));
//        newOne.setOrderId(1L);
        newOne.setUserId(1L);
        newOne.setRoomId(1L);
        newOne.setArrivalDate(Date.valueOf("1980-01-01"));
        newOne.setEventsDate(Date.valueOf("1988-01-10"));
        newOne.setTotal(1000L);
//        newOne.setStatus(EntitiesEnums.BillStatusType.UNPAID);
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

        try {
            newOneOrder = orderDao.save(newOneOrder);
            newOne.setOrderId(newOneOrder.getId());


            saved = dao.save(newOne);
            getIt = dao.get(saved.getId());

            assertEquals(saved, getIt);

            int delNumber = dao.delete(getIt.getId());
            assertEquals(delNumber, 1);
            getIt = dao.get(saved.getId());
            assertNull(getIt);

        } finally {
            con.rollback();
        }
    }

    @Test
    public void update() throws  SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);
        Bill saved;
        Bill updated;
        Bill newOneForSave = new Bill();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOneForSave.setDate(Date.valueOf("2017-08-24"));
//        newOneForSave.setOrderId(1L);
        newOneForSave.setUserId(1L);
        newOneForSave.setRoomId(1L);
        newOneForSave.setArrivalDate(Date.valueOf("1980-01-01"));
        newOneForSave.setEventsDate(Date.valueOf("1988-01-10"));
        newOneForSave.setStatus(BillStatusType.UNPAID);
        newOneForSave.setTotal(1000L);

        Bill newOneForUpdate = new Bill();
        newOneForUpdate.setDate(Date.valueOf("2017-08-22"));
//        newOneForUpdate.setOrderId(3L);
        newOneForUpdate.setUserId(2L);
        newOneForUpdate.setRoomId(3L);
        newOneForUpdate.setArrivalDate(Date.valueOf("1985-01-13"));
        newOneForUpdate.setEventsDate(Date.valueOf("1985-11-10"));
        newOneForUpdate.setStatus(BillStatusType.PAID);
        newOneForUpdate.setTotal(1000L);

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

        try {
            dao.update(null);  // как здесь? метод ничего не делает

            newOneOrder = orderDao.save(newOneOrder);
            newOneForSave.setOrderId(newOneOrder.getId());

            saved = dao.save(newOneForSave);
            newOneForUpdate.setId(saved.getId());
            newOneForUpdate.setOrderId(newOneOrder.getId());

            dao.update(newOneForUpdate);
            updated = dao.get(saved.getId());

            assertNotEquals(saved, updated);
            assertNotNull(updated);
            assertEquals(newOneForUpdate,updated);
            assertEquals(saved.getId(),updated.getId());

        } finally {
            con.rollback();
        }
    }

    @Test
    public void getAllForUserId() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Bill newOneForSave = new Bill();
        newOneForSave.setDate(Date.valueOf("2017-08-24"));
        newOneForSave.setOrderId(1L);
        newOneForSave.setUserId(3L);
        newOneForSave.setRoomId(1L);
        newOneForSave.setArrivalDate(Date.valueOf("1980-01-01"));
        newOneForSave.setEventsDate(Date.valueOf("1988-01-10"));
        newOneForSave.setStatus(BillStatusType.UNPAID);
        newOneForSave.setTotal(1000L);


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
        try {
            List<Bill> list1 = dao.getAllForUserId(3L);

            newOneOrder = orderDao.save(newOneOrder);
            newOneForSave.setOrderId(newOneOrder.getId());
            dao.save(newOneForSave);
            dao.save(newOneForSave);
            newOneForSave.setUserId(1L);
            dao.save(newOneForSave);
            List<Bill> list2 = dao.getAllForUserId(3L);

            assertEquals(list2.size() - list1.size() , 2);

            for (Bill element : list2) {
                dao.delete(element.getId());
            }
            List<Bill> listAfterDeleteAll = dao.getAllForUserId(3L);
            assertEquals(listAfterDeleteAll.size(),0);
        } finally {
            con.rollback();
        }
    }

    @Test
    public void getAllForBillStatus() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Bill newOneForSave = new Bill();
        newOneForSave.setDate(Date.valueOf("2017-08-24"));
        newOneForSave.setOrderId(1L);
        newOneForSave.setUserId(3L);
        newOneForSave.setRoomId(1L);
        newOneForSave.setArrivalDate(Date.valueOf("1980-01-01"));
        newOneForSave.setEventsDate(Date.valueOf("1988-01-10"));
//        newOneForSave.setStatus(EntitiesEnums.BillStatusType.UNPAID);
        newOneForSave.setTotal(1000L);


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
        try {

            List<Bill> list1 = dao.getAllForBillStatus(BillStatusType.UNPAID);

            newOneOrder = orderDao.save(newOneOrder);
            newOneForSave.setOrderId(newOneOrder.getId());

            newOneForSave.setStatus(BillStatusType.UNPAID);
            dao.save(newOneForSave);
            dao.save(newOneForSave);

            newOneForSave.setStatus(BillStatusType.PAID);
            dao.save(newOneForSave);

            List<Bill> list2 = dao.getAllForBillStatus(BillStatusType.UNPAID);

            assertEquals(list2.size() - list1.size(), 2);

        } finally {
            con.rollback();
        }
    }

}
