package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.services.HotelService;
import by.khadasevich.hotel.dao.HotelDao;
import by.khadasevich.hotel.entities.Hotel;
import by.khadasevich.hotel.services.ServiceException;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public final class HotelServiceImpl
        extends AbstractService implements HotelService {

    /**
     * HotelDao instance as singleton.
     */
    private final HotelDao hotelDao =
            SingletonBuilder.getInstanceImpl(HotelDao.class);

    private HotelServiceImpl() { }

    /**
     * Get Hotel by id from database service.
     * @param hotelId is Hotel id
     * @return Hotel entity
     */
    @Override
    public Hotel get(final Serializable hotelId) {
        try {
            return hotelDao.get(hotelId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting Hotel by id" + hotelId);
        }
    }

    /**
     * Get all Hotels from database service.
     * @return List of Hotels
     */
    @Override
    public List<Hotel> getAll() {
        try {
            return hotelDao.getAll();
        } catch (SQLException e) {
            throw new ServiceException("Error getting all Hotels ");
        }
    }
}
