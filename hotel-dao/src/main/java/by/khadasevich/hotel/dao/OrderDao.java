package by.khadasevich.hotel.dao;

import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends DAO<Order> {
    /**
     * Get all Orders from data base for definite User.
     * @param userId is User id
     * @return List of all Orders for definite User
     * @throws SQLException
     */
    List<Order> getAllForUserId(Serializable userId) throws SQLException;

    /**
     * Get all Orders with definite status from data base.
     * @param status is Order status
     * @return List of all Orders from data base with definite status
     * @throws SQLException
     */
    List<Order> getAllForStatus(OrderStatusType status) throws SQLException;

    /**
     * Get all Orders from data base what booked Rooms.
     * @param roomTypeId is Room type id
     * @param arrivalDate is arrival to Hotel date
     * @param eventsDate is events from Hotel date
     * @return List of all Orders what booked Rooms for definite time period
     * @throws SQLException
     */
    List<Order> getAllBooked(Serializable roomTypeId, Date arrivalDate,
                             Date eventsDate) throws SQLException;
}
