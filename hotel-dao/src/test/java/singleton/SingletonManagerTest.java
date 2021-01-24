package singleton;

import by.khadasevich.hotel.dao.UserDao;
import by.khadasevich.hotel.entities.Admin;
import by.khadasevich.hotel.singleton.SingletonBuilder;
import by.khadasevich.hotel.singleton.SingletonException;
import org.junit.Assert;
import org.junit.Test;

public class SingletonManagerTest extends Assert {

    @Test
    public void getInstanceImpl() {
        UserDao userDao1 =  SingletonBuilder.getInstanceImpl(UserDao.class);
        UserDao userDao2 =  SingletonBuilder.getInstanceImpl(UserDao.class);
        assertEquals(userDao1, userDao2);
        assertNotNull(userDao1);
        assertNotNull(userDao2);
    }

    @Test (expected = SingletonException.class)
    public void getInstanceImplNullExeption() {
        SingletonBuilder.getInstanceImpl(null);
    }

    @Test (expected = SingletonException.class)
    public void getInstanceImplNoImplementation() {
        SingletonBuilder.getInstanceImpl(Admin.class);
    }
}
