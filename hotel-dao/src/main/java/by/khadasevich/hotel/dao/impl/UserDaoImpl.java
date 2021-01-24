package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.dao.UserDao;
import by.khadasevich.hotel.entities.User;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class UserDaoImpl extends AbstractDao implements UserDao {
    /**
     * Message is "Can't execute SQL = ".
     */
    private static final String CAN_NOT_EXECUTE_SQL = "Can't execute SQL = ";
    /**
     * Save User query.
     */
    private static final String SAVE_USER_SQL =
           "INSERT INTO users (users_name, users_surname, users_birthDate,"
                   + " users_email, users_password) VALUES (?,?,?,?,?)";
    /**
     * Update User by id query.
     */
    private static final String UPDATE_USER_BY_ID_SQL =
           "UPDATE users SET users_name=?, users_surname=?, users_birthDate=?,"
                   + " users_email=?, users_password=? WHERE users_id=?";
    /**
     * Get User by id query.
     */
    private static final String GET_USER_BY_ID_SQL =
            "SELECT * FROM users WHERE users_id=?";
    /**
     * Get all Users query.
     */
    private static final String GET_ALL_USERS_SQL = "SELECT * FROM users";
    /**
     * Get User by email query.
     */
    private static final String GET_USER_BY_EMAIL_SQL =
            "SELECT * FROM users WHERE users_email=?";
    /**
     * Delete User by id query.
     */
    private static final String DELETE_USER_BY_ID_SQL =
            "DELETE FROM users WHERE users_id=?";

    /**
     * Save User PreparedStatement.
     */
    private PreparedStatement psSave = null;
    /**
     * Update User PreparedStatement.
     */
    private PreparedStatement psUpdate = null;
    /**
     * Get User PreparedStatement.
     */
    private PreparedStatement psGet = null;
    /**
     * Get User by email PreparedStatement.
     */
    private PreparedStatement psGetByEmail = null;
    /**
     * Get all Users PreparedStatement.
     */
    private PreparedStatement psGetAll = null;
    /**
     * Delete User PreparedStatement.
     */
    private PreparedStatement psDelete = null;

    private UserDaoImpl() {
    }

    /**
     * Save User in data base.
     * @param user is User for save
     * @return User after save with id
     * @throws SQLException
     */
    @Override
    public User save(final User user) throws SQLException {
        if (psSave == null) {
            psSave = prepareStatement(SAVE_USER_SQL,
                    Statement.RETURN_GENERATED_KEYS);
        }
        psSave.setString(1, user.getName());
        psSave.setString(2, user.getSurName());
        psSave.setDate(3, user.getBirthDate());
        psSave.setString(4, user.getEmail());
        psSave.setString(5, user.getPassword());
        psSave.executeUpdate();

        try (ResultSet rs = psSave.getGeneratedKeys()) {
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psSave
                    + e.getMessage());
        }
        return user;
    }

    /**
     * Get User from data base with definite id.
     * @param id is User id
     * @return User from data base
     * @throws SQLException
     */
    @Override
    public User get(final Serializable id) throws SQLException {
        if (psGet == null) {
            psGet = prepareStatement(GET_USER_BY_ID_SQL);
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
     * Update User in data base.
     * @param user is User for update
     * @throws SQLException
     */
    @Override
    public void update(final User user) throws SQLException {
        if (user == null) {
            return;
        }
        try {
            if (psUpdate == null) {
                psUpdate = prepareStatement(UPDATE_USER_BY_ID_SQL);
            }
            psUpdate.setLong(6, user.getId());
            psUpdate.setString(1, user.getName());
            psUpdate.setString(2, user.getSurName());
            psUpdate.setDate(3, user.getBirthDate());
            psUpdate.setString(4, user.getEmail());
            psUpdate.setString(5, user.getPassword());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psUpdate
                    + e.getMessage());
        }
    }

    /**
     * Delete User with definite id from data base.
     * @param id is User id in data base
     * @return number of deleted rows
     * @throws SQLException
     */
    @Override
    public int delete(final Serializable id) throws SQLException {
        try {
            if (psDelete == null) {
                psDelete = prepareStatement(DELETE_USER_BY_ID_SQL);
            }
            psDelete.setLong(1, (long) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psDelete
                    + e.getMessage());
        }
    }

    /**
     * Get User with definite email from data base.
     * @param email is unique User email
     * @return User from data base with unique email
     * @throws SQLException
     */
    @Override
    public User getUserByEmail(final String email) throws SQLException {
        try {
            if (psGetByEmail == null) {
                psGetByEmail = prepareStatement(GET_USER_BY_EMAIL_SQL);
            }
            psGetByEmail.setString(1, email);
            psGetByEmail.execute();
            try (ResultSet rs = psGetByEmail.getResultSet()) {
                if (rs.next()) {

                    return populateEntity(rs);
                }
            }  catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetByEmail
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetByEmail
                    + e.getMessage());
        }

        return null;
    }

    /**
     * Get all Users from data base.
     * @return List of all Users from data base
     * @throws SQLException
     */
    @Override
    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try {
            if (psGetAll == null) {
                psGetAll = prepareStatement(GET_ALL_USERS_SQL);
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

    /**
     * Check if User with unique email exist in data base.
     * @param email is unique User email
     * @return true if User present, otherwise false.
     * @throws SQLException
     */
    @Override
    public boolean existWithEmail(final String email) throws SQLException {
        boolean flag = false;
        try {
            if (psGetByEmail == null) {
                psGetByEmail = prepareStatement(GET_USER_BY_EMAIL_SQL);
            }
            psGetByEmail.setString(1, email);
            try (ResultSet rs = psGetByEmail.executeQuery()) {
                if (rs.next()) {
                    flag = true;
                }
            } catch (SQLException e) {
                throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetByEmail
                        + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException(CAN_NOT_EXECUTE_SQL + psGetByEmail
                    + e.getMessage());
        }
        return flag;
    }

    private User populateEntity(final ResultSet rs) throws SQLException {
        User entity = new User();
        entity.setId(rs.getLong(1));
        entity.setName(rs.getString(2));
        entity.setSurName(rs.getString(3));
        entity.setBirthDate(rs.getDate(4));
        entity.setEmail(rs.getString(5));
        entity.setPassword(rs.getString(6));

        return entity;
    }
}
