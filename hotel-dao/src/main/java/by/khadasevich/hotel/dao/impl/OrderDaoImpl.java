package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.dao.OrderDao;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class OrderDaoImpl extends AbstractDao implements OrderDao {
    /**
     * Message is "Can't execute SQL = ".
     */
    private static final String CAN_NOT_EXECUTE_SQL = "Can't execute SQL = ";
    /**
     * Save Order query.
     */
    private static final String SAVE_ORDER_SQL =
            "INSERT INTO `order` (order_dateProcessing, order_admin_id,"
                    + " order_status, order_users_id, order_roomType_id,"
                    + " order_room_id, order_arrivalDate, order_eventsDate,"
                    + " order_total, order_bill_id )"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)";
    /**
     * Get Order by id query.
     */
    private static final String GET_ORDER_BY_ID_SQL =
            "SELECT * FROM `order` WHERE order_id=?";
    /**
     * Get all Orders for definite User query.
     */
    private static final String GET_ALL_ORDER_FOR_USER_ID_SQL =
            "SELECT * FROM `order` WHERE order_users_id=?";
    /**
     * Get all Orders with definite status query.
     */
    private static final String GET_ALL_ORDER_FOR_STATUS_SQL =
            "SELECT * FROM `order` WHERE order_status=?";
    /**
     * Get all Orders what booked Rooms query.
     */
    private static final String GET_ALL_BOOKED_SQL =
            "SELECT * FROM `order` WHERE (order_status=? OR order_status=?)"
                    + " AND order_roomType_id=?"
                    + " AND ( "
                    + "(order_arrivalDate <= ? AND ? < order_eventsDate) or "
                    + "(order_arrivalDate < ? AND ? <= order_eventsDate) or "
                    + "(? <= order_arrivalDate AND order_eventsDate <= ?) or "
                    + "(order_arrivalDate <= ? AND ? <= order_eventsDate)"
                    + ") ";
    /**
     * Update Order by id query.
     */
    private static final String UPDATE_ORDER_BY_ID_SQL =
            "UPDATE `order` SET order_dateProcessing=?, order_admin_id=?,"
                    + " order_status=?, order_users_id=?, order_roomType_id=?,"
                    + " order_room_id=?, order_arrivalDate=?,"
                    + " order_eventsDate=?, order_total=?, order_bill_id=?"
                    + " WHERE order_id=?";
    /**
     * Delete Order by id query.
     */
    private static final String DELETE_ORDER_BY_ID_SQL =
            "DELETE FROM `order` WHERE order_id=?";

    /**
     * Save Order PreparedStatement.
     */
    private PreparedStatement psSave = null;
    /**
     * Update Order PreparedStatement.
     */
    private PreparedStatement psUpdate = null;
    /**
     * Get Order PreparedStatement.
     */
    private PreparedStatement psGet = null;
    /**
     * Delete Order PreparedStatement.
     */
    private PreparedStatement psDelete = null;
    /**
     * Get all Orders for definite Ussr query.
     */
    private PreparedStatement psGetAllForUserId = null;
    /**
     * Get all Orders with definite status query.
     */
    private PreparedStatement psGetAllForStatus = null;
    /**
     * Get all Orders what booked Rooms query.
     */
    private PreparedStatement psGetAllBooked = null;

    private OrderDaoImpl() {
    }

    /**
     * Save Order in data base.
     * @param order is Order to save
     * @return saved Order with id
     * @throws SQLException
     */
    @Override
    public Order save(final Order order) throws SQLException {
        if (psSave == null) {
            psSave = prepareStatement(SAVE_ORDER_SQL,
                    Statement.RETURN_GENERATED_KEYS);
        }
        psSave.setDate(1, order.getDateProcessing());
        psSave.setLong(2, order.getAdminId());
        psSave.setString(3, String.valueOf(order.getStatus()));
        psSave.setLong(4, order.getUserId());
        psSave.setLong(5, order.getRoomTypeId());
        psSave.setLong(6, order.getRoomId());
        psSave.setDate(7, order.getArrivalDate());
        psSave.setDate(8, order.getEventsDate());
        psSave.setLong(9, order.getTotal());
        psSave.setLong(10, order.getBillId());
        psSave.executeUpdate();

        try (ResultSet rs = psSave.getGeneratedKeys()) {
            if (rs.next()) {
                order.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psSave
                    + e.getMessage());
        }
        return order;
    }

    /**
     * Get Order with definite id from data base.
     * @param id is Order id
     * @return Order instance from data base
     * @throws SQLException
     */
    @Override
    public Order get(final Serializable id) throws SQLException {
        if (psGet == null) {
            psGet = prepareStatement(GET_ORDER_BY_ID_SQL);
        }
        psGet.setLong(1, (long) id);
        psGet.executeQuery();

        try (ResultSet rs = psGet.getResultSet()) {
            if (rs.next()) {
                return populateEntity(rs);
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGet
                    + e.getMessage());
        }
        return null;
    }

    /**
     * Update Order in data base.
     * @param order is Order to update
     * @throws SQLException
     */
    @Override
    public void update(final Order order) throws SQLException {
        if (order == null) {
            return;
        }
        try {
            if (psUpdate == null) {
                psUpdate = prepareStatement(UPDATE_ORDER_BY_ID_SQL);
            }
            psUpdate.setLong(11, order.getId());
            psUpdate.setDate(1, order.getDateProcessing());
            psUpdate.setLong(2, order.getAdminId());
            psUpdate.setString(3, String.valueOf(order.getStatus()));
            psUpdate.setLong(4, order.getUserId());
            psUpdate.setLong(5, order.getRoomTypeId());
            psUpdate.setLong(6, order.getRoomId());
            psUpdate.setDate(7, order.getArrivalDate());
            psUpdate.setDate(8, order.getEventsDate());
            psUpdate.setLong(9, order.getTotal());
            psUpdate.setLong(10, order.getBillId());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psUpdate
                    + e.getMessage());
        }
    }

    /**
     * Delete Order with definite id from data base.
     * @param id is Order id in data base
     * @return number of deleted rows
     * @throws SQLException
     */
    @Override
    public int delete(final Serializable id) throws SQLException {
        try {
            if (psDelete == null) {
                psDelete = prepareStatement(DELETE_ORDER_BY_ID_SQL);
            }
            psDelete.setLong(1, (long) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psDelete
                    + e.getMessage());
        }
    }

    /**
     * Get all Order from data base for definite User.
     * @param userId is User id
     * @return List of Orders for definite Users
     * @throws SQLException
     */
    @Override
    public List<Order> getAllForUserId(final Serializable userId)
            throws SQLException {
        List<Order> list = new ArrayList<>();
        try {
            if (psGetAllForUserId == null) {
                psGetAllForUserId =
                        prepareStatement(GET_ALL_ORDER_FOR_USER_ID_SQL);
            }
            psGetAllForUserId.setLong(1, (long) userId);
            psGetAllForUserId.execute();
            try (ResultSet rs = psGetAllForUserId.getResultSet()) {
                while (rs.next()) {
                    list.add(populateEntity(rs));
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForUserId
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForUserId
                    + e.getMessage());
        }

        return list;
    }

    /**
     * Get all Orders from data base with definite status.
     * @param status is Order status
     * @return List of Orders with definite status
     * @throws SQLException
     */
    @Override
    public List<Order> getAllForStatus(final OrderStatusType status)
            throws SQLException {
        List<Order> list = new ArrayList<>();
        try {
            if (psGetAllForStatus == null) {
                psGetAllForStatus =
                        prepareStatement(GET_ALL_ORDER_FOR_STATUS_SQL);
            }
            psGetAllForStatus.setString(1, String.valueOf(status));
            psGetAllForStatus.execute();
            try (ResultSet rs = psGetAllForStatus.getResultSet()) {
                while (rs.next()) {
                    list.add(populateEntity(rs));
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForStatus
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForStatus
                    + e.getMessage());
        }

        return list;
    }

    /**
     * Get all Orders from data base what booked Rooms.
     * @param roomTypeId is Room type id
     * @param arrivalDate is arrival to Hotel date
     * @param eventsDate is events from Hotel date
     * @return List of all Orders what booked Rooms for definite time period
     * @throws SQLException
     */
    @Override
    public List<Order> getAllBooked(final Serializable roomTypeId,
                                    final Date arrivalDate,
                                    final Date eventsDate) throws SQLException {
        List<Order> list = new ArrayList<>();
        try {
            if (psGetAllBooked == null) {
                psGetAllBooked = prepareStatement(GET_ALL_BOOKED_SQL);
            }
            psGetAllBooked.setString(1,
                    String.valueOf(OrderStatusType.APPROVED));
            psGetAllBooked.setString(2, String.valueOf(OrderStatusType.PAID));
            psGetAllBooked.setLong(3, (long) roomTypeId);
            psGetAllBooked.setDate(4, arrivalDate);
            psGetAllBooked.setDate(5, arrivalDate);
            psGetAllBooked.setDate(6, eventsDate);
            psGetAllBooked.setDate(7, eventsDate);
            psGetAllBooked.setDate(8, arrivalDate);
            psGetAllBooked.setDate(9, eventsDate);
            psGetAllBooked.setDate(10, arrivalDate);
            psGetAllBooked.setDate(11, eventsDate);
            psGetAllBooked.execute();
            try (ResultSet rs = psGetAllBooked.getResultSet()) {
                while (rs.next()) {
                    list.add(populateEntity(rs));
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllBooked
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllBooked
                    + e.getMessage());
        }

        return list;
    }


    private Order populateEntity(final ResultSet rs) throws SQLException {
        Order entity = new Order();
        entity.setId(rs.getLong(1));
        entity.setDateProcessing(rs.getDate(2));
        entity.setAdminId(rs.getLong(3));
        entity.setStatus(OrderStatusType.valueOf(rs.getString(4)));
        entity.setUserId(rs.getLong(5));
        entity.setRoomTypeId(rs.getLong(6));
        entity.setRoomId(rs.getLong(7));
        entity.setArrivalDate(rs.getDate(8));
        entity.setEventsDate(rs.getDate(9));
        entity.setTotal(rs.getLong(10));
        entity.setBillId(rs.getLong(11));

        return entity;
    }

}
