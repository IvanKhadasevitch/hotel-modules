package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.dao.AdminDao;
import by.khadasevich.hotel.entities.Admin;
import by.khadasevich.hotel.services.AdminService;
import by.khadasevich.hotel.services.ServiceException;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.io.Serializable;
import java.sql.SQLException;

public final class AdminServiceImpl
        extends AbstractService implements AdminService {

    /**
     * AdminDao instance as singleton.
     */
    private final AdminDao adminDao =
            SingletonBuilder.getInstanceImpl(AdminDao.class);

    private AdminServiceImpl() { }

    /**
     * Get Admin from database by id.
     * @param adminId is Admin id
     * @return Admin entity
     */
    @Override
    public Admin get(final Serializable adminId) {
        try {
            return adminDao.get(adminId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting Admin by id " + adminId);
        }
    }

}
