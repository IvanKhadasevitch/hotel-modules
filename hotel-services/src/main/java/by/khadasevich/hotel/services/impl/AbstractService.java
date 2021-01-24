package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.db.DbManagerException;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractService {
    /**
     * Start transaction for definite connection.
     * @throws SQLException
     */
    public void startTransaction() throws SQLException {
        ConnectionManager.getConnection().setAutoCommit(false);
    }

    /**
     * Finished transaction for definite connection.
     * @throws SQLException
     */
    public void stopTransaction() throws SQLException {
        ConnectionManager.getConnection().setAutoCommit(true);
    }

    /**
     * Commit changes in database.
     * @throws SQLException
     */
    public void commit() throws SQLException {
        ConnectionManager.getConnection().commit();
    }

    /**
     * Get connection to database.
     * @return Connection instance
     */
    public Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    /**
     * Rollback transaction.
     */
    public void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            throw new DbManagerException("rollback error");
        }
    }
}
