package dao.impl;

import by.khadasevich.hotel.dao.AdminDao;
import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.entities.Admin;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminDaoImpTest extends Assert {
    private AdminDao dao = SingletonBuilder.getInstanceImpl(AdminDao.class);

    @Test
    public void saveGetDelete() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);
        Admin saved;
        Admin getIt;

        Admin newOne = new Admin();
        newOne.setHotelId(1L);
        newOne.setName("Vasy");
        newOne.setPassword("Pypkin password");

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
        Admin saved;
        Admin updated;
        Admin newOneForSave = new Admin();
        newOneForSave.setHotelId(1L);
        newOneForSave.setName("Vasy");
        newOneForSave.setPassword("Pypkin password");

        Admin newOneForUpdate = new Admin();
        newOneForUpdate.setHotelId(2L);
        newOneForUpdate.setName("Vasy 123");
        newOneForUpdate.setPassword("Pypkin password 123");

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

}
