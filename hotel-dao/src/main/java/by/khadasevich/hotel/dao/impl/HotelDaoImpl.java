package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.dao.HotelDao;
import by.khadasevich.hotel.entities.Hotel;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class HotelDaoImpl extends AbstractDao implements HotelDao {
    /**
     * Message is "Can't execute SQL = ".
     */
    private static final String CAN_NOT_EXECUTE_SQL = "Can't execute SQL = ";
    /**
     * Save Hotel query.
     */
    private static final String SAVE_HOTEL_SQL =
            "INSERT INTO hotel (hotel_name, hotel_email) VALUES (?,?)";
    /**
     * Get Hotel bu id query.
     */
    private static final String GET_HOTEL_BY_ID_SQL =
            "SELECT * FROM hotel WHERE hotel_id=?";
    /**
     * Get all Hotels query.
     */
    private static final String GET_ALL_HOTEL_SQL = "SELECT * FROM hotel";
    /**
     * Update Hotel by id query.
     */
    private static final String UPDATE_HOTEL_BY_ID_SQL =
            "UPDATE hotel SET hotel_name=?, hotel_email=? WHERE hotel_id=?";
    /**
     * Delete Hotel by id query.
     */
    private static final String DELETE_HOTEL_BY_ID_SQL =
            "DELETE FROM hotel WHERE hotel_id=?";

    /**
     * Save Hotel PreparedStatement.
     */
    private PreparedStatement psSave = null;
    /**
     * Update Hotel PreparedStatement.
     */
    private PreparedStatement psUpdate = null;
    /**
     * Get Hotel PreparedStatement.
     */
    private PreparedStatement psGet = null;
    /**
     * Get all Hotels PreparedStatement.
     */
    private PreparedStatement psGetAll = null;
    /**
     * Delete Hotel PreparedStatement.
     */
    private PreparedStatement psDelete = null;

    private HotelDaoImpl() {
    }

    /**
     * Save Hotel in data base.
     * @param hotel is Hotel to save
     * @return saved Hotel with id
     * @throws SQLException
     */
    @Override
    public Hotel save(final Hotel hotel) throws SQLException {
        if (psSave == null) {
            psSave = prepareStatement(SAVE_HOTEL_SQL,
                    Statement.RETURN_GENERATED_KEYS);
        }
        psSave.setString(1, hotel.getName());
        psSave.setString(2, hotel.getEmail());
        psSave.executeUpdate();

        try (ResultSet rs = psSave.getGeneratedKeys()) {
            if (rs.next()) {
                hotel.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psSave
                    + e.getMessage());
        }
        return hotel;
    }

    /**
     * Get Hotel from data base.
     * @param id is Hotel id
     * @return Hotel instance from data base
     * @throws SQLException
     */
    @Override
    public Hotel get(final Serializable id) throws SQLException {
        if (psGet == null) {
            psGet = prepareStatement(GET_HOTEL_BY_ID_SQL);
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
     * Update Hotel in data base.
     * @param hotel is Hotel to update
     * @throws SQLException
     */
    @Override
    public void update(final Hotel hotel) throws SQLException {
        if (hotel == null) {
            return;
        }
        try {
            if (psUpdate == null) {
                psUpdate = prepareStatement(UPDATE_HOTEL_BY_ID_SQL);
            }
            psUpdate.setLong(3, hotel.getId());
            psUpdate.setString(1, hotel.getName());
            psUpdate.setString(2, hotel.getEmail());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psUpdate
                    + e.getMessage());
        }
    }

    /**
     * Delete Hotel with id=id from data base.
     * @param id is Hotel id in data base
     * @return number of deleted rows
     * @throws SQLException
     */
    @Override
    public int delete(final Serializable id) throws SQLException {
        try {
            if (psDelete == null) {
                psDelete = prepareStatement(DELETE_HOTEL_BY_ID_SQL);
            }
            psDelete.setLong(1, (long) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psDelete
                    + e.getMessage());
        }
    }

    /**
     * Get all Hotels from data base.
     * @return List of all Hotels from data base
     * @throws SQLException
     */
    @Override
    public List<Hotel> getAll() throws SQLException {
        List<Hotel> list = new ArrayList<>();
        try {
            if (psGetAll == null) {
                psGetAll = prepareStatement(GET_ALL_HOTEL_SQL);
            }
            psGetAll.execute();
            try (ResultSet rs = psGetAll.getResultSet()) {
                while (rs.next()) {
                    list.add(populateEntity(rs));
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAll
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAll
                    + e.getMessage());
        }

        return list;
    }
    private Hotel populateEntity(final ResultSet rs) throws SQLException {
        Hotel entity = new Hotel();
        entity.setId(rs.getLong(1));
        entity.setName(rs.getString(2));
        entity.setEmail(rs.getString(3));

        return entity;
    }
}
