package by.khadasevich.hotel.db;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import by.khadasevich.hotel.db.pool.ConnectionPool;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Class DataSource.
 */
public final class DataSource {
    /**
     * DataSource instance as singleton.
     */
    private static volatile DataSource INSTANCE = null;
    /**
     * Pooled datasource, use c3p0.
     */
//    private ComboPooledDataSource pooledDatasource;
    private ConnectionPool pooledDatasource;

    /**
     * Data base url.
     */
    private final String URL;
    /**
     * Data base driver.
     */
    private final String DRIVER;
    /**
     * User of database login.
     */
    private final String USER;
    /**
     * User of database password.
     */
    private final String PASSWORD;

    {
        ResourceBundle rb = ResourceBundle.getBundle("db_hotel");
        if (rb == null) {
            URL = "UNDEFINED";
            USER = "UNDEFINED";
            PASSWORD = "UNDEFINED";
            DRIVER = "com.mysql.jdbc.Driver";
        } else {
            URL = rb.getString("url");
            USER = rb.getString("login");
            PASSWORD = rb.getString("password");
            DRIVER = rb.getString("driver");
        }
    }

    private DataSource()
    {
//            throws IOException, SQLException, PropertyVetoException {
//        pooledDatasource = new ComboPooledDataSource();
//        pooledDatasource.setDriverClass(DRIVER); //loads the jdbc driver
//        pooledDatasource.setJdbcUrl(URL);
//        pooledDatasource.setUser(USER);
//        pooledDatasource.setPassword(PASSWORD);
//
//        // the settings below are optional -- c3p0 can work with defaults
//        pooledDatasource.setMinPoolSize(10);
//        pooledDatasource.setAcquireIncrement(5);
//        pooledDatasource.setMaxPoolSize(20);
//        pooledDatasource.setMaxStatements(180);

        pooledDatasource = ConnectionPool.getInstance();
        pooledDatasource.init(DRIVER, URL, USER, PASSWORD,
                3, 25, 60*60*5);
    }

//    public static synchronized DataSource getInstance()
//            throws IOException, SQLException, PropertyVetoException {
//        if (datasource == null) {
//            datasource = new DataSource();
//            return datasource;
//        } else {
//            return datasource;
//        }
//    }

    /**
     * Get instance of datasource.
     * @return Datasource instance.
     * @throws PropertyVetoException
     * @throws SQLException
     * @throws IOException
     */
    public static DataSource getInstance()
            throws PropertyVetoException, SQLException, IOException {
        DataSource instance = INSTANCE;
        if (instance == null) {
            synchronized (DataSource.class) {
                instance = INSTANCE;
                if (instance == null) {
                    INSTANCE = instance = new DataSource();
                }
            }
        }

        return instance;
    }

    /**
     * Get Connection from polled datasource.
     * @return Connection to data base
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return pooledDatasource.getConnection();
    }
}
