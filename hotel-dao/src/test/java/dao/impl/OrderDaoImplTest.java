package dao.impl;

import by.khadasevich.hotel.dao.OrderDao;
import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImplTest extends Assert {
    private OrderDao dao = SingletonBuilder.getInstanceImpl(OrderDao.class);

    @Test
    public void saveGetDelete() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

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
        newOne.setTotal(100L);

        try {
            saved = dao.save(newOne);
            getIt = dao.get(saved.getId());

            assertEquals(saved, getIt);
            assertNotNull(getIt);

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

        Order saved;
        Order updated;
        Order newOneForSave = new Order();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOneForSave.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneForSave.setAdminId(1L);
        newOneForSave.setStatus(OrderStatusType.APPROVED);
        newOneForSave.setUserId(3L);
        newOneForSave.setRoomTypeId(1L);
        newOneForSave.setRoomId(1L);
        newOneForSave.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneForSave.setEventsDate(Date.valueOf("1990-01-10"));
        newOneForSave.setTotal(100L);

        Order newOneForUpdate = new Order();
        newOneForUpdate.setDateProcessing(Date.valueOf("1981-12-21"));
        newOneForUpdate.setAdminId(1L);
        newOneForUpdate.setStatus(OrderStatusType.NEW);
        newOneForUpdate.setUserId(1L);
        newOneForUpdate.setRoomTypeId(1L);
        newOneForUpdate.setRoomId(3L);
        newOneForUpdate.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneForUpdate.setEventsDate(Date.valueOf("1988-01-10"));
        newOneForUpdate.setTotal(300L);

        try {
            dao.update(null);  // как здесь? метод ничего не делает

            saved = dao.save(newOneForSave);
            newOneForUpdate.setId(saved.getId());

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

        Order newOneForSave = new Order();
        newOneForSave.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneForSave.setAdminId(1L);
        newOneForSave.setStatus(OrderStatusType.APPROVED);
        newOneForSave.setUserId(3L);
        newOneForSave.setRoomTypeId(1L);
        newOneForSave.setRoomId(1L);
        newOneForSave.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneForSave.setEventsDate(Date.valueOf("1990-01-10"));
        newOneForSave.setTotal(300L);
        try {
            List<Order> list1 = dao.getAllForUserId(3L);
            dao.save(newOneForSave);
            dao.save(newOneForSave);
            newOneForSave.setUserId(1L);
            dao.save(newOneForSave);
            List<Order> list2 = dao.getAllForUserId(3L);

            assertEquals(list2.size() - list1.size() , 2);

        } finally {
            con.rollback();
        }
    }

    @Test
    public void getAllForStatus() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Order newOneForSave = new Order();
        newOneForSave.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneForSave.setAdminId(1L);
        newOneForSave.setStatus(OrderStatusType.DECLINE);
        newOneForSave.setUserId(3L);
        newOneForSave.setRoomTypeId(1L);
        newOneForSave.setRoomId(1L);
        newOneForSave.setArrivalDate(Date.valueOf("1990-01-01"));
        newOneForSave.setEventsDate(Date.valueOf("1990-01-10"));
        newOneForSave.setTotal(300L);
        try {
            List<Order> list1 = dao.getAllForStatus(OrderStatusType.DECLINE);
            dao.save(newOneForSave);
            dao.save(newOneForSave);
            newOneForSave.setStatus(OrderStatusType.NEW);
            dao.save(newOneForSave);

            List<Order> list2 = dao.getAllForStatus(OrderStatusType.DECLINE);

            assertEquals(list2.size() - list1.size() , 2);

            for (Order element : list2) {
                dao.delete(element.getId());
            }
            List<Order> listAfterDeleteAll = dao.getAllForStatus(OrderStatusType.DECLINE);
            assertEquals(listAfterDeleteAll.size(),0);

        } finally {
            con.rollback();
        }
    }

    @Test
    public void getAllBooked() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Order newOneForSave = new Order();
        newOneForSave.setDateProcessing(Date.valueOf("1990-01-01"));
        newOneForSave.setAdminId(1L);
//        newOneForSave.setStatus(EntitiesEnums.OrderStatusType.NEW);
        newOneForSave.setUserId(3L);
        newOneForSave.setRoomTypeId(5L);
        newOneForSave.setRoomId(1L);
//        newOneForSave.setArrivalDate(Date.valueOf("1990-01-01"));
//        newOneForSave.setEventsDate(Date.valueOf("1990-01-10"));
        newOneForSave.setTotal(300L);
        try {
            List<Order> list1 = dao
                    .getAllBooked(5l,
                            Date.valueOf("1980-01-01"), Date.valueOf("1980-01-10"));
            newOneForSave.setStatus(OrderStatusType.NEW);
            newOneForSave.setArrivalDate(Date.valueOf("1980-01-01"));
            newOneForSave.setEventsDate(Date.valueOf("1980-01-10"));
            dao.save(newOneForSave);

            newOneForSave.setStatus(OrderStatusType.APPROVED);
            newOneForSave.setArrivalDate(Date.valueOf("1980-01-01"));
            newOneForSave.setEventsDate(Date.valueOf("1980-01-12"));
            dao.save(newOneForSave);

            newOneForSave.setStatus(OrderStatusType.APPROVED);
            newOneForSave.setArrivalDate(Date.valueOf("1979-01-01"));
            newOneForSave.setEventsDate(Date.valueOf("1980-01-10"));
            dao.save(newOneForSave);

            newOneForSave.setStatus(OrderStatusType.PAID);
            newOneForSave.setArrivalDate(Date.valueOf("1980-01-03"));
            newOneForSave.setEventsDate(Date.valueOf("1980-01-10"));
            dao.save(newOneForSave);

            newOneForSave.setStatus(OrderStatusType.APPROVED);
            newOneForSave.setArrivalDate(Date.valueOf("1979-01-01"));
            newOneForSave.setEventsDate(Date.valueOf("1980-01-12"));
            dao.save(newOneForSave);

            List<Order> list2 = dao.getAllBooked(5l,
                            Date.valueOf("1980-01-01"), Date.valueOf("1980-01-10"));

            assertEquals(list2.size() - list1.size() , 4);

        } finally {
            con.rollback();
        }
    }
}
