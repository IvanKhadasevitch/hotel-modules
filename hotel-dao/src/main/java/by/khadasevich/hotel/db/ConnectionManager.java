package by.khadasevich.hotel.db;

import java.sql.Connection;

/**
 * Class ConnectionManager.
 *
 */
public final class ConnectionManager {
    /**
     * TreadLocal variable to store Connection.
     */
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    private ConnectionManager() {
        // utility class, no realisation needed
    }

    /**
     * Get Connection for current thread.
     * @return Connection instance
     * @throws DbManagerException
     */
    public static Connection getConnection() throws DbManagerException {
        try {
            if (tl.get() == null) {
                tl.set(DataSource.getInstance().getConnection());
            }
            return tl.get();
        } catch (Exception e) {
            throw new DbManagerException("Data base connection error."
                    +  e.getMessage());
        }
    }
}
