package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.dao.BillDao;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.enums.BillStatusType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class BillDaoImpl extends AbstractDao implements BillDao {

    /**
     * Message is "Can't execute SQL = ".
     */
    private static final String CAN_NOT_EXECUTE_SQL = "Can't execute SQL = ";
    /**
     * Save Bill query.
     */
    private static final String SAVE_BILL_SQL =
            "INSERT INTO bill (bill_data, bill_order_id, bill_users_id,"
                    + " bill_room_id, bill_arrivalDate, bill_eventsDate,"
                    + " bill_total, bill_status )"
                    + " VALUES (?,?,?,?,?,?,?,?)";
    /**
     * Get Bill by id query.
     */
    private static final String GET_BILL_BY_ID_SQL =
            "SELECT * FROM bill WHERE bill_id=?";
    /**
     * Get all Bills for user query.
     */
    private static final String GET_ALL_BILL_FOR_USER_ID_SQL =
            "SELECT * FROM bill WHERE bill_users_id=?";
    /**
     * Get all Bills with definite status query.
     */
    private static final String GET_ALL_BILL_FOR_STATUS_SQL =
            "SELECT * FROM bill WHERE bill_status=?";
    /**
     * Update Bill by id query.
     */
    private static final String UPDATE_BILL_BY_ID_SQL =
            "UPDATE `bill` SET bill_data=?, bill_order_id=?, bill_users_id=?,"
                    + " bill_room_id=?, bill_arrivalDate=?, bill_eventsDate=?,"
                    + " bill_total=?, bill_status=?  WHERE bill_id=?";
    /**
     * Delete Bill by id query.
     */
    private static final String DELETE_BILL_BY_ID_SQL =
            "DELETE FROM bill WHERE bill_id=?";

    /**
     * Save Bill PreparedStatement.
     */
    private PreparedStatement psSave = null;
    /**
     * Update Bill PreparedStatement.
     */
    private PreparedStatement psUpdate = null;
    /**
     * Get Bill PreparedStatement.
     */
    private PreparedStatement psGet = null;
    /**
     * Delete Bill PreparedStatement.
     */
    private PreparedStatement psDelete = null;
    /**
     * Get all Bills for definite User PreparedStatement.
     */
    private PreparedStatement psGetAllForUserId = null;
    /**
     * Get all Bills with definite status PreparedStatement.
     */
    private PreparedStatement psGetAllForBillStatus = null;

    private BillDaoImpl() {
    }

    /**
     * Save Bill in data base.
     * @param bill is Bill to save.
     * @return saved Bill with id
     * @throws SQLException
     */
    @Override
    public Bill save(final Bill bill) throws SQLException {
        if (psSave == null) {
            psSave = prepareStatement(SAVE_BILL_SQL,
                    Statement.RETURN_GENERATED_KEYS);
        }
        psSave.setDate(1, bill.getDate());
        psSave.setLong(2, bill.getOrderId());
        psSave.setLong(3, bill.getUserId());
        psSave.setLong(4, bill.getRoomId());
        psSave.setDate(5, bill.getArrivalDate());
        psSave.setDate(6, bill.getEventsDate());
        psSave.setLong(7, bill.getTotal());
        psSave.setString(8, String.valueOf(bill.getStatus()));
        psSave.executeUpdate();

        try (ResultSet rs = psSave.getGeneratedKeys()) {
            if (rs.next()) {
                bill.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psSave
                    + e.getMessage());
        }
        return bill;
    }

    /**
     * Get Bill with id=id from data base.
     * @param id is Bill id
     * @return Bill object
     * @throws SQLException
     */
    @Override
    public Bill get(final Serializable id) throws SQLException {
        if (psGet == null) {
            psGet = prepareStatement(GET_BILL_BY_ID_SQL);
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
     * Update record in data base for Bill.
     * @param bill is Bill for update in data base
     * @throws SQLException
     */
    @Override
    public void update(final Bill bill) throws SQLException {
        if (bill == null) {
            return;
        }
        try {
            if (psUpdate == null) {
                psUpdate = prepareStatement(UPDATE_BILL_BY_ID_SQL);
            }
            psUpdate.setLong(9, bill.getId());
            psUpdate.setDate(1, bill.getDate());
            psUpdate.setLong(2, bill.getOrderId());
            psUpdate.setLong(3, bill.getUserId());
            psUpdate.setLong(4, bill.getRoomId());
            psUpdate.setDate(5, bill.getArrivalDate());
            psUpdate.setDate(6, bill.getEventsDate());
            psUpdate.setLong(7, bill.getTotal());
            psUpdate.setString(8, String.valueOf(bill.getStatus()));
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psUpdate
                    + e.getMessage());
        }
    }

    /**
     * Delete Bill in data base with id=id.
     * @param id is record id in data base
     * @return number of delete rows
     * @throws SQLException
     */
    @Override
    public int delete(final Serializable id) throws SQLException {
        try {
            if (psDelete == null) {
                psDelete = prepareStatement(DELETE_BILL_BY_ID_SQL);
            }
            psDelete.setLong(1, (long) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psDelete
                    + e.getMessage());
        }
    }

    /**
     * Get all Bills from data base for definite User.
     * @param userId is User id
     * @return List of Bills for definite User
     * @throws SQLException
     */
    @Override
    public List<Bill> getAllForUserId(final Serializable userId)
            throws SQLException {
        List<Bill> list = new ArrayList<>();
        try {
            if (psGetAllForUserId == null) {
                psGetAllForUserId =
                        prepareStatement(GET_ALL_BILL_FOR_USER_ID_SQL);
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
     * Get all Bills from data base with definite status.
     * @param billStatus is Bill status.
     * @return List of Bills from data base with definite status
     * @throws SQLException
     */
    @Override
    public List<Bill> getAllForBillStatus(final BillStatusType billStatus)
            throws SQLException {
        List<Bill> list = new ArrayList<>();
        try {
            if (psGetAllForBillStatus == null) {
                psGetAllForBillStatus =
                        prepareStatement(GET_ALL_BILL_FOR_STATUS_SQL);
            }
            psGetAllForBillStatus.setString(1, String.valueOf(billStatus));
            psGetAllForBillStatus.execute();
            try (ResultSet rs = psGetAllForBillStatus.getResultSet()) {
                while (rs.next()) {
                    list.add(populateEntity(rs));
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL
                        + psGetAllForBillStatus + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForBillStatus
                    + e.getMessage());
        }

        return list;
    }

    private Bill populateEntity(final ResultSet rs) throws SQLException {
        Bill entity = new Bill();

        entity.setId(rs.getLong(1));
        entity.setDate(rs.getDate(2));
        entity.setOrderId(rs.getLong(3));
        entity.setUserId(rs.getLong(4));
        entity.setRoomId(rs.getLong(5));
        entity.setArrivalDate(rs.getDate(6));
        entity.setEventsDate(rs.getDate(7));
        entity.setTotal(rs.getLong(8));
        entity.setStatus(BillStatusType.valueOf(rs.getString(9)));
        return entity;
    }

}

