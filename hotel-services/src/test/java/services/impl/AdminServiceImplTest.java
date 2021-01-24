package services.impl;

import by.khadasevich.hotel.entities.Admin;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.services.AdminService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.sql.SQLException;

public class AdminServiceImplTest extends Assert {
    private AdminService adminService = SingletonBuilder.getInstanceImpl(AdminService.class);

    @Test
    public void get() throws SQLException {

        Admin getIt;
            getIt = adminService.get(1L);

            assertEquals(1L, getIt.getId());
            assertEquals(1L, getIt.getHotelId());
            assertEquals("Cool Admin negro", getIt.getName());
            assertEquals("xMpCOKC5I4INzFCab3WEmw==", getIt.getPassword());
            assertNotNull(getIt);
    }
}
