package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.services.RoomTypeService;
import by.khadasevich.hotel.dao.RoomTypeDao;
import by.khadasevich.hotel.entities.RoomType;
import by.khadasevich.hotel.services.ServiceException;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public final class RoomTypeServiceImpl
        extends AbstractService implements RoomTypeService {

    /**
     * RoomTypeDao as singleton instance.
     */
    private final RoomTypeDao roomTypeDao =
            SingletonBuilder.getInstanceImpl(RoomTypeDao.class);

    private RoomTypeServiceImpl() { }

    /**
     * Get RoomType with definite id from database service.
     * @param id is RoomType id
     * @return RoomType entity
     */
    @Override
    public RoomType get(final Serializable id) {
        try {
            return roomTypeDao.get(id);
        } catch (SQLException e) {
            throw new ServiceException("Error getting RoomType by id" + id);
        }
    }

    /**
     * Get all RoomTypes for definite Hotel service.
     * @param hotelId is Hotel id
     * @return List of all RoomTypes for definite Hotel
     */
    @Override
    public List<RoomType> getAllForHotel(final Serializable hotelId) {
        try {
            return roomTypeDao.getAllForHotel(hotelId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting all RoomTypes ");
        }
    }
}
