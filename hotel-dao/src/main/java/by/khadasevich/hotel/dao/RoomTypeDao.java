package by.khadasevich.hotel.dao;

import by.khadasevich.hotel.entities.RoomType;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface RoomTypeDao extends DAO<RoomType> {
    /**
     * Get all RoomType for definite Hotel.
     * @param hotelId is Hotel id
     * @return List of all RoomTypes for definite Hotel
     * @throws SQLException
     */
    List<RoomType> getAllForHotel(Serializable hotelId) throws SQLException;
}
