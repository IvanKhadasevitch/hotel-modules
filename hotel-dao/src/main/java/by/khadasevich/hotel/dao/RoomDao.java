package by.khadasevich.hotel.dao;

import by.khadasevich.hotel.entities.Room;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface RoomDao extends DAO<Room> {
    /**
     * Get all Rooms from data base with definite RoomType.
     * @param id is RoomType id
     * @return List of Rooms from data base with definite RoomType
     * @throws SQLException
     */
    List<Room> getAllForRoomType(Serializable id) throws SQLException;
}
