package dao.impl;

import by.khadasevich.hotel.dao.UserDao;
import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.entities.User;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImplTest extends Assert {
    private UserDao dao = SingletonBuilder.getInstanceImpl(UserDao.class);

    @Test
    public void saveGetDelete() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);
        User saved;
        User getIt;
        User newOne = new User();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOne.setName("Vasy");
        newOne.setSurName("Pypkin");
        newOne.setBirthDate(Date.valueOf("1990-12-1"));
        newOne.setEmail("pypkin@gmail.com");
        newOne.setPassword("Pypkin");

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
        User saved;
        User updated;
        User newOneForSave = new User();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOneForSave.setName("Vasy");
        newOneForSave.setSurName("Pypkin");
        newOneForSave.setBirthDate(Date.valueOf("1990-12-1"));
        newOneForSave.setEmail("pypkin@gmail.com");
        newOneForSave.setPassword("Pypkin");

        User newOneForUpdate = new User();
        newOneForUpdate.setName("Vasy 2");
        newOneForUpdate.setSurName("Pypkin 2");
        newOneForUpdate.setBirthDate(Date.valueOf("1990-10-12"));
        newOneForUpdate.setEmail("pypkin 123 @gmail.com");
        newOneForUpdate.setPassword("Pypkin 2");
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
    public void getUserByEmail() throws  SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);
        User saved;
        User getIt;
        User newOneForSave = new User();
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        newOneForSave.setName("Vasy");
        newOneForSave.setSurName("Pypkin");
        newOneForSave.setBirthDate(Date.valueOf("1990-12-1"));
        newOneForSave.setEmail("pypkin@gmail.com");
        newOneForSave.setPassword("Pypkin");

        try {
            getIt = dao.getUserByEmail(null);
            assertNull(getIt);

            saved = dao.save(newOneForSave);
            getIt = dao.getUserByEmail(saved.getEmail());
            assertEquals(saved, getIt);
            assertNotNull(getIt);

        } finally {
            con.rollback();
        }
    }

    @Test
    public void getAll() throws  SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        User newOneForSave = new User();
        newOneForSave.setName("Vasy");
        newOneForSave.setSurName("Pypkin");
        newOneForSave.setBirthDate(Date.valueOf("1990-12-1"));
        newOneForSave.setEmail("pypkin@gmail.com");
        newOneForSave.setPassword("Pypkin");
        try {
            List<User> list1 = dao.getAll();

            dao.save(newOneForSave);
            newOneForSave.setEmail("pypkin 2 @gmail.com");
            dao.save(newOneForSave);

            List<User> list2 = dao.getAll();
            assertEquals(list2.size() - list1.size(), 2);

        } finally {
            con.rollback();
        }
    }
}
