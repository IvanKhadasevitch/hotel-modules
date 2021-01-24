package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.services.RoomService;
import by.khadasevich.hotel.dao.OrderDao;
import by.khadasevich.hotel.dao.RoomDao;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.Room;
import by.khadasevich.hotel.services.ServiceException;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class RoomServiceImpl
        extends AbstractService implements RoomService {

    /**
     * RoomDao singleton instance.
     */
    private final RoomDao roomDao =
            SingletonBuilder.getInstanceImpl(RoomDao.class);
    /**
     * OrderDao singleton instance.
     */
    private final OrderDao orderDao =
            SingletonBuilder.getInstanceImpl(OrderDao.class);

    private RoomServiceImpl() { }

    /**
     * Get Room with definite id from database service.
     * @param roomId is Room id
     * @return Room entity
     */
    @Override
    public Room get(final Serializable roomId) {
        try {
            return roomDao.get(roomId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting Room by id" + roomId);
        }
    }

    /**
     * Get all unbooked Rooms of definite RoomType for some time period.
     * @param roomTypeId is RoomTyp
     * @param arrivalDate is time period start (include)
     * @param eventsDate is time period finished (include)
     * @return List of unbooked Rooms
     */
    @Override
    public List<Room> getAllFreeForRoomType(final Serializable roomTypeId,
                                            final Date arrivalDate,
                                            final Date eventsDate) {
        try {
            List<Room> allRoomsForRoomType =
                    roomDao.getAllForRoomType(roomTypeId);
            List<Long> allBookedRoomId = orderDao
                    .getAllBooked(roomTypeId, arrivalDate, eventsDate)
                    .stream()
                    .map(Order::getRoomId)
                    .collect(Collectors.toList());

//            if (allBookedRoomId.size() > 0) {
            if (!allBookedRoomId.isEmpty()) {

                return allRoomsForRoomType
                    .stream()
//                   .peek(System.out::println)
                    .filter(entry -> !allBookedRoomId.contains(entry.getId()))
//                   .peek(System.out::println)
                    .collect(Collectors.toList());
            } else {

                return allRoomsForRoomType;
            }
        } catch (SQLException e) {
            throw new ServiceException("Error getting all free rooms"
                    + " for RoomTypeId  " + roomTypeId);
        }
    }

}
