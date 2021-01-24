package by.khadasevich.hotel.dao.impl;

import by.khadasevich.hotel.dao.AdminDao;
import by.khadasevich.hotel.entities.Admin;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDaoImpl extends AbstractDao implements AdminDao {

    private static final String SAVE_ADMIN_SQL =
            "INSERT INTO admin (admin_hotel_id, admin_name, admin_password) VALUES (?,?,?)";
    private static final String GET_ADMIN_BY_ID_SQL = "SELECT * FROM admin WHERE admin_id=?";
    private static final String UPDATE_ADMIN_BY_ID_SQL =
            "UPDATE admin SET admin_hotel_id=?, admin_name=?, admin_password=? WHERE admin_id=?";
    private static final String DELETE_ADMIN_BY_ID_SQL = "DELETE FROM admin WHERE admin_id=?";

    private PreparedStatement psSave = null;
    private PreparedStatement psUpdate = null;
    private PreparedStatement psGet = null;
    private PreparedStatement psDelete = null;

    private AdminDaoImpl() {
    }

    @Override
    public Admin save(Admin admin) throws SQLException {
        if (psSave == null) {
            psSave = prepareStatement(SAVE_ADMIN_SQL, Statement.RETURN_GENERATED_KEYS);
        }
        psSave.setLong(1, admin.getHotelId());
        psSave.setString(2, admin.getName());
        psSave.setString(3, admin.getPassword());
        psSave.executeUpdate();

        try (ResultSet rs = psSave.getGeneratedKeys()) {
            if (rs.next()) {
                admin.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Can't execute SQL = " + psSave + e.getMessage());
        }
        return admin;
    }

    @Override
    public Admin get(Serializable id) throws SQLException {
        if (psGet == null) {
            psGet = prepareStatement(GET_ADMIN_BY_ID_SQL);
        }
        psGet.setLong(1, (long) id);
        psGet.executeQuery();

        try (ResultSet rs = psGet.getResultSet()) {
            if (rs.next()) {
                return populateEntity(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Can't execute SQL = " + psGet + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Admin admin) throws SQLException {
        if (admin == null) {
            return;
        }
        try {
            if (psUpdate == null) {
                psUpdate = prepareStatement(UPDATE_ADMIN_BY_ID_SQL);
            }
            psUpdate.setLong(4, admin.getId());
            psUpdate.setLong(1, admin.getHotelId());
            psUpdate.setString(2, admin.getName());
            psUpdate.setString(3, admin.getPassword());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Can't execute SQL = " + psUpdate + e.getMessage());
        }
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        try {
            if (psDelete == null) {
                psDelete = prepareStatement(DELETE_ADMIN_BY_ID_SQL);
            }
            psDelete.setLong(1, (long) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Can't execute SQL = " + psDelete + e.getMessage());
        }
    }

    private Admin populateEntity(ResultSet rs) throws SQLException {
        Admin entity = new Admin();
        entity.setId(rs.getLong(1));
        entity.setHotelId(rs.getLong(2));
        entity.setName(rs.getString(3));
        entity.setPassword(rs.getString(4));

        return entity;
    }

}
