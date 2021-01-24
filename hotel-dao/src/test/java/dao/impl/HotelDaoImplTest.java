package dao.impl;

import by.khadasevich.hotel.dao.HotelDao;
import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.entities.Hotel;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HotelDaoImplTest extends Assert {
    private HotelDao dao = SingletonBuilder.getInstanceImpl(HotelDao.class);

    @Test
    public void saveGetDelete() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Hotel saved;
        Hotel getIt;
        Hotel newOne = new Hotel();
        newOne.setName("Vasy");
        newOne.setEmail("pypkin@gmail.com");

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

        Hotel saved;
        Hotel updated;
        Hotel newOneForSave = new Hotel();
        newOneForSave.setName("Vasy");
        newOneForSave.setEmail("pypkin@gmail.com");

        Hotel newOneForUpdate = new Hotel();
        newOneForUpdate.setName("Vasy 23");
        newOneForUpdate.setEmail("pypkin 2345 @gmail.com");

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
    public void getAll() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        Hotel newOneForSave = new Hotel();
        newOneForSave.setName("Vasy");
        newOneForSave.setEmail("pypkin@gmail.com");
        try {
            List<Hotel> list1 = dao.getAll();

            dao.save(newOneForSave);
            dao.save(newOneForSave);

            List<Hotel> list2 = dao.getAll();
            assertEquals(list2.size() - list1.size(), 2);

        } finally {
            con.rollback();
        }
    }

}
