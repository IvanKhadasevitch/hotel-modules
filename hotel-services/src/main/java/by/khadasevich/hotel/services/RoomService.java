package by.khadasevich.hotel.services;

import by.khadasevich.hotel.entities.Room;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public interface RoomService {
    /**
     * Get Room with definite id from database service.
     * @param roomId is Room id
     * @return Room entity
     */
    Room get(Serializable roomId);

    /**
     * Get all unbooked Rooms of definite RoomType for some time period.
     * @param roomTypeId is RoomTyp
     * @param arrivalDate is time period start (include)
     * @param eventsDate is time period finished (include)
     * @return List of unbooked Rooms
     */
    List<Room> getAllFreeForRoomType(Serializable roomTypeId,
                                     Date arrivalDate, Date eventsDate);
}
