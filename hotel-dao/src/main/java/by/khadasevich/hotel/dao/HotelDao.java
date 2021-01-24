package by.khadasevich.hotel.dao;

import by.khadasevich.hotel.entities.Hotel;

import java.sql.SQLException;
import java.util.List;

public interface HotelDao extends DAO<Hotel> {
    /**
     * Get all Hotels from data base.
     * @return List of Hotels from data base
     * @throws SQLException
     */
    List<Hotel> getAll() throws SQLException;
}
