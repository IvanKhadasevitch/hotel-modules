package by.khadasevich.hotel.dao;

import by.khadasevich.hotel.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends DAO<User> {
    /**
     * Get User from data base by email.
     * @param email is unique User email
     * @return User instance or Null if not exist
     * @throws SQLException
     */
    User getUserByEmail(String email) throws SQLException;

    /**
     * Get all Users from data base.
     * @return List of all Users in data base
     * @throws SQLException
     */
    List<User> getAll() throws SQLException;

    /**
     * Detect if User with definite email present in data base.
     * @param email is unique User email
     * @return true if User with email present in data base, otherwise false
     * @throws SQLException
     */
    boolean existWithEmail(String email) throws SQLException;
}
