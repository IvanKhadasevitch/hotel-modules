package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDao {
    /**
     * Provides PreparedStatement for query.
     * @param query is query
     * @return PreparedStatement for definite query.
     * @throws SQLException
     */
    protected PreparedStatement prepareStatement(final String query)
            throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query);
    }

    /**
     * Provides PreparedStatement for query.
     * @param query is query
     * @param flag
     * @return PreparedStatement for definite query.
     * @throws SQLException
     */
    protected PreparedStatement prepareStatement(final String query,
                                                 final int flag)
            throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query, flag);
    }

//    protected void close(ResultSet rs) {
//        try {
//            if (rs != null)
//                rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
