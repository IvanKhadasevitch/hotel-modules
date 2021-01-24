package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.dao.RoomTypeDao;
import by.khadasevich.hotel.entities.RoomType;
import by.khadasevich.hotel.entities.enums.CurrencyType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class RoomTypeDaoImpl extends AbstractDao implements RoomTypeDao {
    /**
     * Message is "Can't execute SQL = ".
     */
    private static final String CAN_NOT_EXECUTE_SQL = "Can't execute SQL = ";
    /**
     * Save RoomType query.
     */
    private static final String SAVE_ROOM_TYPE_SQL =
            "INSERT INTO roomType (roomType_name, roomType_seats,"
                    + " roomType_price, roomType_currency, roomType_hotel_id)"
                    + " VALUES (?,?,?,?,?)";
    /**
     * Get RoomType by is query.
     */
    private static final String GET_ROOM_TYPE_BY_ID_SQL =
            "SELECT * FROM roomType WHERE roomType_id=?";
    /**
     * Get all RoomTypes for definite Hotel query.
     */
    private static final String GET_ALL_ROOM_TYPE_FOR_HOTEL_SQL =
            "SELECT * FROM roomType WHERE roomType_hotel_id=?";
    /**
     * Update RoomType with definite id query.
     */
    private static final String UPDATE_ROOM_TYPE_BY_ID_SQL =
            "UPDATE roomType SET roomType_name=?, roomType_seats=?,"
                    + " roomType_price=?, roomType_currency=?,"
                    + " roomType_hotel_id=? WHERE roomType_id=?";
    /**
     * Delete RoomType by id query.
     */
    private static final String DELETE_ROOM_TYPE_BY_ID_SQL =
            "DELETE FROM roomType WHERE roomType_id=?";

    /**
     * Save RoomType PreparedStatement.
     */
    private PreparedStatement psSave = null;
    /**
     * Update RoomType PreparedStatement.
     */
    private PreparedStatement psUpdate = null;
    /**
     * Get RoomType PreparedStatement.
     */
    private PreparedStatement psGet = null;
    /**
     * Delete RoomType PreparedStatement.
     */
    private PreparedStatement psDelete = null;
    /**
     * Get all RoomTypes for definite Hotel PreparedStatement.
     */
    private PreparedStatement psGetAllForHotel = null;

    private RoomTypeDaoImpl() {
    }

    /**
     * Save RoomType in data base.
     * @param roomType is RoomType for save
     * @return saved RoomType with id
     * @throws SQLException
     */
    @Override
    public RoomType save(final RoomType roomType) throws SQLException {
        if (psSave == null) {
            psSave = prepareStatement(SAVE_ROOM_TYPE_SQL,
                    Statement.RETURN_GENERATED_KEYS);
        }
        psSave.setString(1, roomType.getName());
        psSave.setInt(2, roomType.getSeats());
        psSave.setInt(3, roomType.getPrice());
        psSave.setString(4, String.valueOf(roomType.getCurrency()));
        psSave.setLong(5, roomType.getHotelId());
        psSave.executeUpdate();

        try (ResultSet rs = psSave.getGeneratedKeys()) {
            if (rs.next()) {
                roomType.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psSave
                    + e.getMessage());
        }
        return roomType;
    }

    /**
     * Get RoomType with definte id from data base.
     * @param id is RoomType id
     * @return RoomType from data base
     * @throws SQLException
     */
    @Override
    public RoomType get(final Serializable id) throws SQLException {
        if (psGet == null) {
            psGet = prepareStatement(GET_ROOM_TYPE_BY_ID_SQL);
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
     * Update RoomType in data base.
     * @param roomType is RoomType for update
     * @throws SQLException
     */
    @Override
    public void update(final RoomType roomType) throws SQLException {
        if (roomType == null) {
            return;
        }
        try {
            if (psUpdate == null) {
                psUpdate = prepareStatement(UPDATE_ROOM_TYPE_BY_ID_SQL);
            }
            psUpdate.setLong(6, roomType.getId());
            psUpdate.setString(1, roomType.getName());
            psUpdate.setInt(2, roomType.getSeats());
            psUpdate.setInt(3, roomType.getPrice());
            psUpdate.setString(4, String.valueOf(roomType.getCurrency()));
            psUpdate.setLong(5, roomType.getHotelId());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psUpdate
                    + e.getMessage());
        }
    }

    /**
     * Deleta RoomType from data base by id.
     * @param id is RoomType id in data base
     * @return number of deleted rows
     * @throws SQLException
     */
    @Override
    public int delete(final Serializable id) throws SQLException {
        try {
            if (psDelete == null) {
                psDelete = prepareStatement(DELETE_ROOM_TYPE_BY_ID_SQL);
            }
            psDelete.setLong(1, (long) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psDelete
                    + e.getMessage());
        }
    }

    /**
     * Get all RoomTypes from data base for definite Hotel.
     * @param hotelId is Hotel id
     * @return List all RoomTypes for definite Hotel
     * @throws SQLException
     */
    @Override
    public List<RoomType> getAllForHotel(final Serializable hotelId)
            throws SQLException {
        List<RoomType> list = new ArrayList<>();
        try {
            if (psGetAllForHotel == null) {
                psGetAllForHotel =
                        prepareStatement(GET_ALL_ROOM_TYPE_FOR_HOTEL_SQL);
            }
            psGetAllForHotel.setLong(1, (long) hotelId);
            psGetAllForHotel.execute();
            try (ResultSet rs = psGetAllForHotel.getResultSet()) {
                while (rs.next()) {
                    list.add(populateEntity(rs));
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForHotel
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForHotel
                    + e.getMessage());
        }

        return list;
    }

    private RoomType populateEntity(final ResultSet rs) throws SQLException {
        RoomType entity = new RoomType();
        entity.setId(rs.getLong(1));
        entity.setName(rs.getString(2));
        entity.setSeats(rs.getInt(3));
        entity.setPrice(rs.getInt(4));
        entity.setCurrency(CurrencyType.valueOf(rs.getString(5)));
        entity.setHotelId(rs.getLong(6));

        return entity;
    }

}
