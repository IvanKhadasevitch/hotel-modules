package services.impl;

import by.khadasevich.hotel.entities.RoomType;
import by.khadasevich.hotel.entities.enums.CurrencyType;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.services.RoomTypeService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.util.List;

public class RoomTypeServiceImplTest extends Assert {
    private RoomTypeService roomTypeService = SingletonBuilder.getInstanceImpl(RoomTypeService.class);

    @Test
    public void get() {
        RoomType getIt = roomTypeService.get(1L);

        assertNotNull(getIt);
        assertEquals("ONE", getIt.getName());
        assertEquals(1L, getIt.getHotelId());
        assertEquals(3000, getIt.getPrice());
        assertEquals(1, getIt.getSeats());
        assertEquals(CurrencyType.USD, getIt.getCurrency());
    }

    @Test
    public void getAllForHotel() {
        List<RoomType> roomTypeList = roomTypeService.getAllForHotel(1L);
        assertEquals(roomTypeList.size(), 5);
        roomTypeList = roomTypeService.getAllForHotel(2L);
        assertEquals(roomTypeList.size(), 2);
    }
}
