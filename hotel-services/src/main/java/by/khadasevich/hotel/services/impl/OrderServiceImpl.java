package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.services.RoomService;
import by.khadasevich.hotel.dao.BillDao;
import by.khadasevich.hotel.dao.OrderDao;
import by.khadasevich.hotel.dto.OrderDto;
import by.khadasevich.hotel.dto.mappers.OrderDtoMapper;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.Room;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import by.khadasevich.hotel.services.ServiceException;
import by.khadasevich.hotel.singleton.SingletonBuilder;
import by.khadasevich.hotel.entities.enums.BillStatusType;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public final class OrderServiceImpl
        extends AbstractService implements OrderService {

    /**
     * OrderDao instance as singleton.
     */
    private final OrderDao orderDao =
            SingletonBuilder.getInstanceImpl(OrderDao.class);
    /**
     * BillDao instance as singleton.
     */
    private final BillDao billDao =
            SingletonBuilder.getInstanceImpl(BillDao.class);
    /**
     * RoomService instance as singleton.
     */
    private final RoomService roomService =
            SingletonBuilder.getInstanceImpl(RoomService.class);

    private OrderServiceImpl() { }

    /**
     * Get Order by id from database service.
     * @param orderId is Order id
     * @return Order entity
     */
    @Override
    public Order get(final Serializable orderId) {
        try {
            return orderDao.get(orderId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting Order by id " + orderId);
        }
    }

    /**
     * Delete Order with definite id from database service.
     * @param orderId is Order id
     * @return number of deleted rows
     */
    @Override
    public int delete(final Serializable orderId) {
        int numberDeletedOrders = 0;
        try {
            this.startTransaction();
            numberDeletedOrders = orderDao.delete(orderId);
            this.commit();
            this.stopTransaction();
            return numberDeletedOrders;
        } catch (SQLException e) {
            rollback();
            throw new ServiceException("Error deleting Order by id "
                    + orderId);
        }
    }

    /**
     * Creat Order with definite RoomType, arrival and events dates.
     * @param roomTypeId is RoomType
     * @param arrivalDate is arrival guest to Hotel date
     * @param eventsDate is events guest fro Hotel date
     * @return Order entity
     */
    @Override
    public Order make(final Serializable roomTypeId,
                      final Date arrivalDate, final Date eventsDate) {
        Order order = new Order();
        order.setStatus(OrderStatusType.NEW);
        order.setRoomTypeId((long) roomTypeId);
        order.setArrivalDate(arrivalDate);
        order.setEventsDate(eventsDate);

        return order;
    }

    /**
     * Save Order for definite User in database service.
     * @param order is Order to save
     * @param userId is User id
     * @return Order entity with id
     */
    @Override
    public  Order save(Order order, final Serializable userId) {
        order.setUserId((long) userId);
        try {
            this.startTransaction();
            Order orderSave = orderDao.save(order);
            this.commit();
            this.stopTransaction();
            return orderSave;
        } catch (SQLException e) {
            rollback();
            throw new ServiceException("Error making Order " + order);
        }
    }

    /**
     * Decline Order with definite id by definite Admin service.
     * @param orderId is Order id
     * @param adminId is Admin id
     * @return true if operation success, otherwise false
     */
    @Override
    public boolean decline(final Serializable orderId,
                           final Serializable adminId) {
        try {
            this.startTransaction();

            Order order = orderDao.get(orderId);
            if (order == null) {
                this.stopTransaction();

                return false;
            }
            // check what order NEW then decline
            OrderStatusType saveOrderStatus =
                    OrderStatusType.NEW.equals(order.getStatus())
                        ? OrderStatusType.DECLINE
                        : order.getStatus();
            order.setAdminId((long) adminId);
            order.setStatus(saveOrderStatus);
            orderDao.update(order);
            this.commit();
            this.stopTransaction();

            return OrderStatusType.DECLINE.equals(saveOrderStatus);
        } catch (SQLException e) {
            rollback();
            throw new ServiceException("Error getting Order decline for id "
                    + orderId);
        }

    }

    /**
     * Approve Order with definite id by definite Admin service.
     * @param orderId is Order id
     * @param adminId is Admin id
     * @return Bill entity to pay
     */
    @Override
    public Bill approve(final Serializable orderId,
                        final Serializable adminId) {
        try {
            this.startTransaction();

            Order order = orderDao.get(orderId);
            //order deleted of DB
            if (order == null) {
                this.stopTransaction();

                return null;
            }
            // order not NEW - can't approve
            if (!OrderStatusType.NEW.equals(order.getStatus())) {
                this.stopTransaction();

                return null;
            }
            // make orderDto
            OrderDto orderDto =
                    SingletonBuilder.getInstanceImpl(OrderDtoMapper.class)
                            .map(order);

            // expired Order can't approve
            if (orderDto.isExpired()) {
                this.stopTransaction();

                return null;
            }

            // take free room
            List<Room> freeRoomsList =
                    roomService.getAllFreeForRoomType(order.getRoomTypeId(),
                            order.getArrivalDate(), order.getEventsDate());
            // no free rooms
            if (freeRoomsList.isEmpty()) {
                this.stopTransaction();

                return null;
            }

            //take first free room from list
            Room freeRoom = freeRoomsList.get(0);

            // make bill
            Bill bill = new Bill();
            java.util.TimeZone
                    .setDefault(java.util.TimeZone.getTimeZone("UTC"));
            Date currentDate = new Date((new java.util.Date()).getTime());
            bill.setDate(currentDate);
            bill.setOrderId(order.getId());
            bill.setUserId(order.getUserId());
            bill.setRoomId(freeRoom.getId());
            bill.setArrivalDate(order.getArrivalDate());
            bill.setEventsDate(order.getEventsDate());
            bill.setTotal(order.getTotal());
            bill.setStatus(BillStatusType.UNPAID);

            // save bill in DB
            bill = billDao.save(bill);
            // update order
            order.setAdminId((long) adminId);
            order.setStatus(OrderStatusType.APPROVED);
            order.setRoomId(freeRoom.getId());
            order.setBillId(bill.getId());
            orderDao.update(order);
            this.commit();
            this.stopTransaction();

            return bill;
        } catch (SQLException e) {
            rollback();
            throw new ServiceException("Error getting Order approve for id "
                    + orderId);
        }

    }

    /**
     * Get all Orders from database for definite User service.
     * @param userId is User id
     * @return List of all Orders from database for definite User
     */
    @Override
    public List<Order> getAllForUserId(final Serializable userId) {
        try {
            return orderDao.getAllForUserId(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting all orders"
                    + " for user with id " + userId);
        }
    }

    /**
     * Get all Orders from database with  definite status service.
     * @param status is Order status
     * @return List of all Orders from database with  definite status
     */
    @Override
    public List<Order> getAllForStatus(final OrderStatusType status) {
        try {
            return orderDao.getAllForStatus(status);
        } catch (SQLException e) {
            throw new ServiceException("Error getting all orders with status "
                    + status);
        }
    }
}
