package by.khadasevich.hotel.services;

import by.khadasevich.hotel.entities.Hotel;

import java.io.Serializable;
import java.util.List;

public interface HotelService {
    /**
     * Get Hotel by id from database service.
     * @param hotelId is Hotel id
     * @return Hotel entity
     */
    Hotel get(Serializable hotelId);

    /**
     * Get all Hotels from database service.
     * @return List of Hotels
     */
    List<Hotel> getAll();
}
