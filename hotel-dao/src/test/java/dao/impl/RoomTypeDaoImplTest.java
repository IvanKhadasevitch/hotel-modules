package dao.impl;

import by.khadasevich.hotel.dao.RoomTypeDao;
import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.entities.RoomType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.singleton.SingletonBuilder;
import by.khadasevich.hotel.entities.enums.CurrencyType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomTypeDaoImplTest extends Assert {
    private RoomTypeDao dao = SingletonBuilder.getInstanceImpl(RoomTypeDao.class);

    @Test
    public void saveGetDelete() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        RoomType saved;
        RoomType getIt;
        RoomType newOneForSave = new RoomType();
        newOneForSave.setName("Vasy");
        newOneForSave.setSeats(1);
        newOneForSave.setPrice(10);
        newOneForSave.setCurrency(CurrencyType.EURO);
        newOneForSave.setHotelId(1L);

        try {

            saved = dao.save(newOneForSave);
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

        RoomType saved;
        RoomType updated;
        RoomType newOneForSave = new RoomType();
        newOneForSave.setName("Vasy");
        newOneForSave.setSeats(1);
        newOneForSave.setPrice(10);
        newOneForSave.setCurrency(CurrencyType.EURO);
        newOneForSave.setHotelId(1L);

        RoomType newOneForUpdate = new RoomType();
        newOneForUpdate.setName("Vasy 22");
        newOneForUpdate.setSeats(12);
        newOneForUpdate.setPrice(100);
        newOneForUpdate.setCurrency(CurrencyType.USD);
        newOneForUpdate.setHotelId(2L);

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
    public void getAllForHotel() throws SQLException {
        Connection con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        RoomType newOneForSave = new RoomType();
        newOneForSave.setName("Vasy");
        newOneForSave.setSeats(1);
        newOneForSave.setPrice(10);
        newOneForSave.setCurrency(CurrencyType.EURO);
        newOneForSave.setHotelId(1L);

        try {
            List<RoomType> list1 = dao.getAllForHotel(1L);

            dao.save(newOneForSave);
            dao.save(newOneForSave);

            List<RoomType> list2 = dao.getAllForHotel(1L);
            assertEquals(list2.size() - list1.size(), 2);

        } finally {
            con.rollback();
        }
    }

}
