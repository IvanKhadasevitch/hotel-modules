package by.khadasevich.hotel.services;

import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public interface OrderService {
    /**
     * Get Order by id from database service.
     * @param orderId is Order id
     * @return Order entity
     */
    Order get(Serializable orderId);

    /**
     * Delete Order with definite id from database service.
     * @param orderId is Order id
     * @return number of deleted rows
     */
    int delete(Serializable orderId);

    /**
     * Creat Order with definite RoomType, arrival and events dates.
     * @param roomTypeId is RoomType
     * @param arrivalDate is arrival guest to Hotel date
     * @param eventsDate is events guest fro Hotel date
     * @return Order entity
     */
    Order make(Serializable roomTypeId, Date arrivalDate, Date eventsDate);

    /**
     * Save Order for definite User in database service.
     * @param order is Order to save
     * @param userId is User id
     * @return saved Order entity with id
     */
    Order save(Order order, Serializable userId);

    /**
     * Decline Order with definite id by definite Admin service.
     * @param orderId is Order id
     * @param adminId is Admin id
     * @return true if operation success, otherwise false
     */
    boolean decline(Serializable orderId, Serializable adminId);

    /**
     * Approve Order with definite id by definite Admin service.
     * @param orderId is Order id
     * @param adminId is Admin id
     * @return Bill entity to pay
     */
    Bill approve(Serializable orderId, Serializable adminId);

    /**
     * Get all Orders from database for definite User service.
     * @param userId is User id
     * @return List of all Orders from database for definite User
     */
    List<Order> getAllForUserId(Serializable userId);

    /**
     * Get all Orders from database with  definite status service.
     * @param status is Order status
     * @return List of all Orders from database with  definite status
     */
    List<Order> getAllForStatus(OrderStatusType status);
}
