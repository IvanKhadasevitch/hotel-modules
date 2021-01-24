package services.impl;

import by.khadasevich.hotel.entities.Hotel;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.services.HotelService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.util.List;

public class HotelServiceImplTest extends Assert {
    private HotelService hotelService = SingletonBuilder.getInstanceImpl(HotelService.class);
    @Test
    public void get() {
        Hotel getIt = hotelService.get(1L);

        assertEquals("Negresko Princes", getIt.getName());
        assertEquals("negresko@gmail.com", getIt.getEmail());
        assertNotNull(getIt);
    }

    @Test
    public void getAll() {
        List<Hotel> hotelList = hotelService.getAll();
        assertEquals(hotelList.size(), 2);
    }
}
