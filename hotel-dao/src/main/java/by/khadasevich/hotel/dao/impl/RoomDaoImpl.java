package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.dao.RoomDao;
import by.khadasevich.hotel.entities.Room;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class RoomDaoImpl extends AbstractDao implements RoomDao {
    /**
     * Message is "Can't execute SQL = ".
     */
    private static final String CAN_NOT_EXECUTE_SQL = "Can't execute SQL = ";
    /**
     * Save Room query.
     */
    private static final String SAVE_ROOM_SQL =
            "INSERT INTO room (room_number, room_roomType_id ) VALUES (?,?)";
    /**
     * Get Room bu id query.
     */
    private static final String GET_ROOM_BY_ID_SQL =
            "SELECT * FROM room WHERE room_id=?";
    /**
     * Get all Rooms with definite RoomType query.
     */
    private static final String GET_ALL_ROOM_FOR_ROOM_TYPE_SQL =
            "SELECT * FROM room WHERE room_roomType_id=?";
    /**
     * Update Room with definite id query.
     */
    private static final String UPDATE_ROOM_BY_ID_SQL =
            "UPDATE room SET room_number=?, room_roomType_id=?"
                    + " WHERE room_id=?";
    /**
     * Delete Room by id query.
     */
    private static final String DELETE_ROOM_BY_ID_SQL =
            "DELETE FROM room WHERE room_id=?";

    /**
     * Save Room PreparedStatement.
     */
    private PreparedStatement psSave = null;
    /**
     * Update Room PreparedStatement.
     */
    private PreparedStatement psUpdate = null;
    /**
     * Get Room PreparedStatement.
     */
    private PreparedStatement psGet = null;
    /**
     * Delete Room PreparedStatement.
     */
    private PreparedStatement psDelete = null;
    /**
     * Get all Rooms with dfinite RoomType PreparedStatement.
     */
    private PreparedStatement psGetAllForRoomType = null;

    private RoomDaoImpl() {
    }

    /**
     * Save Room in date base.
     * @param room is Room fo save
     * @return saved Room with id
     * @throws SQLException
     */
    @Override
    public Room save(final Room room) throws SQLException {
        if (psSave == null) {
            psSave = prepareStatement(SAVE_ROOM_SQL,
                    Statement.RETURN_GENERATED_KEYS);
        }
        psSave.setString(1, room.getNumber());
        psSave.setLong(2, room.getRoomTypeId());
        psSave.executeUpdate();

        try (ResultSet rs = psSave.getGeneratedKeys()) {
            if (rs.next()) {
                room.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psSave
                    + e.getMessage());
        }
        return room;
    }

    /**
     * Get Room from data base by id.
     * @param id is Room id
     * @return Room instance with definite id
     * @throws SQLException
     */
    @Override
    public Room get(final Serializable id) throws SQLException {
        if (psGet == null) {
            psGet = prepareStatement(GET_ROOM_BY_ID_SQL);
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
     * Update Room in data base.
     * @param room is Room to update
     * @throws SQLException
     */
    @Override
    public void update(final Room room) throws SQLException {
        if (room == null) {
            return;
        }
        try {
            if (psUpdate == null) {
                psUpdate = prepareStatement(UPDATE_ROOM_BY_ID_SQL);
            }
            psUpdate.setLong(3, room.getId());
            psUpdate.setString(1, room.getNumber());
            psUpdate.setLong(2, room.getRoomTypeId());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psUpdate
                    + e.getMessage());
        }
    }

    /**
     * Delete Room from data base.
     * @param id is Room id in data base
     * @return number of deleted rows
     * @throws SQLException
     */
    @Override
    public int delete(final Serializable id) throws SQLException {
        try {
            if (psDelete == null) {
                psDelete = prepareStatement(DELETE_ROOM_BY_ID_SQL);
            }
            psDelete.setLong(1, (long) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psDelete
                    + e.getMessage());
        }
    }

    /**
     * Get all Rooms from data base with definite RoomType.
     * @param roomTypeId is RoomType id
     * @return List of Rooms with definite RoomType
     * @throws SQLException
     */
    @Override
    public List<Room> getAllForRoomType(final Serializable roomTypeId)
            throws SQLException {
        List<Room> list = new ArrayList<>();
        try {
            if (psGetAllForRoomType == null) {
                psGetAllForRoomType =
                        prepareStatement(GET_ALL_ROOM_FOR_ROOM_TYPE_SQL);
            }
            psGetAllForRoomType.setLong(1, (long) roomTypeId);
            psGetAllForRoomType.execute();
            try (ResultSet rs = psGetAllForRoomType.getResultSet()) {
                while (rs.next()) {
                    list.add(populateEntity(rs));
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForRoomType
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetAllForRoomType
                    + e.getMessage());
        }

        return list;
    }

    private Room populateEntity(final ResultSet rs) throws SQLException {
        Room entity = new Room();
        entity.setId(rs.getLong(1));
        entity.setNumber(rs.getString(2));
        entity.setRoomTypeId(rs.getLong(3));

        return entity;
    }
}
