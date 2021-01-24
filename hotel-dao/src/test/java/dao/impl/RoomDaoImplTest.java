package dao.impl;

import by.khadasevich.hotel.dao.RoomDao;
import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.entities.Room;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomDaoImplTest extends Assert {
    private RoomDao dao = SingletonBuilder.getInstanceImpl(RoomDao.class);

    @Test
    public void saveGetDelete() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Room saved;
        Room getIt;
        Room newOne = new Room();
        newOne.setNumber("123 ab");
        newOne.setRoomTypeId(3L);
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

        Room saved;
        Room updated;
        Room newOneForSave = new Room();
        newOneForSave.setNumber("123 ab");
        newOneForSave.setRoomTypeId(3L);

        Room newOneForUpdate = new Room();
        newOneForUpdate.setNumber("100 ab");
        newOneForUpdate.setRoomTypeId(7L);

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
    public void getAllForRoomType() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Room newOneForSave = new Room();
        newOneForSave.setNumber("123 ab");
        newOneForSave.setRoomTypeId(3L);
        try {
            List<Room> list1 = dao.getAllForRoomType(3L);

            dao.save(newOneForSave);
            dao.save(newOneForSave);

            List<Room> list2 = dao.getAllForRoomType(3L);
            assertEquals(list2.size() - list1.size(), 2);

        } finally {
            con.rollback();
        }
    }

}
