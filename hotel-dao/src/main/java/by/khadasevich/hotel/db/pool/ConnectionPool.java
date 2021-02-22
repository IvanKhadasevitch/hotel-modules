package by.khadasevich.hotel.db.pool;

import by.khadasevich.hotel.db.DbManagerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int checkConnectionTimeout;

    private BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();
    private static ConnectionPool instance = new ConnectionPool();
    private final Lock LOCK = new ReentrantLock();

    private ConnectionPool() {}

    public static ConnectionPool getInstance() {
        return instance;
    }
    public Connection getConnection() throws DbManagerException {
        LOCK.lock();
        PooledConnection connection = null;
        while(connection == null) {
            try {
                if(!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if(!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                        } catch(SQLException e) {
                            LOCK.unlock();
                            logger.error("Can't close connection");
                            throw new DbManagerException("Can't close connection");
                        }
                        connection = null;
                    }
                } else if(usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    LOCK.unlock();
                    logger.error("The limit of number of database connections is exceeded");
                    throw new DbManagerException("The limit of number of database connections is exceeded");
                }
            } catch(InterruptedException | SQLException e) {
                LOCK.unlock();
                logger.error("It is impossible to connect to a database", e);
                throw new DbManagerException(e.getMessage());
            }
        }
        usedConnections.add(connection);
        LOCK.unlock();
        logger.debug(String.format("Connection was received from pool."
                +" Current pool size: %d used connections; %d free connection",
                usedConnections.size(), freeConnections.size()));
        return connection;
    }

    void freeConnection(PooledConnection connection) {
        try {
            LOCK.lock();
            if(connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.debug(String.format("Connection was returned into pool."
                        + " Current pool size: %d used connections; %d free connection",
                        usedConnections.size(), freeConnections.size()));
            }
            LOCK.unlock();
        } catch(SQLException | InterruptedException e1) {
            LOCK.unlock();
            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch(SQLException e2) {
                LOCK.unlock();
                logger.error("Can't close connection");
                throw new DbManagerException("Can't close connection");
            }
        }
    }

    public void init(String driverClass, String url, String user,
                     String password, int startSize, int maxSize,
                     int checkConnectionTimeout) throws DbManagerException {
        try {
            LOCK.lock();
            destroy();
            Class.forName(driverClass);
            this.url = url;
            this.user = user;
            this.password = password;
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for(int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
            LOCK.unlock();
        } catch(ClassNotFoundException | SQLException | InterruptedException e) {
            LOCK.unlock();
            logger.fatal("It is impossible to initialize connection pool", e);
            throw new DbManagerException(e.getMessage());
        }
    }

    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url, user, password));
    }

    public void destroy() {
        LOCK.lock();
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for(PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch(SQLException e) {
                LOCK.unlock();
                logger.error("Can't close connection");
                throw new DbManagerException("Can't close connection");
            }
        }
        usedConnections.clear();
        LOCK.unlock();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }
}
