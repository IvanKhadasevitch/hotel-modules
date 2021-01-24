package by.khadasevich.hotel.services;

import by.khadasevich.hotel.entities.RoomType;

import java.io.Serializable;
import java.util.List;

public interface RoomTypeService {
    /**
     * Get RoomType with definite id from database service.
     * @param id is RoomType id
     * @return RoomType entity
     */
    RoomType get(Serializable id);

    /**
     * Get all RoomTypes for definite Hotel service.
     * @param hotelId is Hotel id
     * @return List of all RoomTypes for definite Hotel
     */
    List<RoomType> getAllForHotel(Serializable hotelId);
}
