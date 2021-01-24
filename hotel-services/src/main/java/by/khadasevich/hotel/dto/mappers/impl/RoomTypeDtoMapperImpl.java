package by.khadasevich.hotel.dto.mappers.impl;

import by.khadasevich.hotel.dto.RoomTypeDto;
import by.khadasevich.hotel.dto.mappers.RoomTypeDtoMapper;
import by.khadasevich.hotel.entities.Hotel;
import by.khadasevich.hotel.entities.RoomType;
import by.khadasevich.hotel.services.HotelService;
import by.khadasevich.hotel.services.RoomTypeService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDtoMapperImpl implements RoomTypeDtoMapper {
    /**
     * RoomTypeService singleton instance.
     */
    private final RoomTypeService roomTypeService  =
            SingletonBuilder.getInstanceImpl(RoomTypeService.class);
    /**
     * HotelService singleton instance.
     */
    private final HotelService hotelService  =
            SingletonBuilder.getInstanceImpl(HotelService.class);

    /**
     * Map All roomType for Hotel with hotelId.
     * Add hotel name and return List of RoomTypeDto
     * @param hotelId is Hotel id
     * @return List of RoomTypeDto for definite Hotel
     */
    @Override
    public List<RoomTypeDto> mapAll(final Serializable hotelId) {
        final String DEFAULT = "NotDefine";

        //take and validate data from DB
        Hotel hotel = hotelService.get(hotelId);
        String hotelName = hotel != null
                ? hotel.getName()
                : DEFAULT;
        List<RoomType> roomTypeList = roomTypeService.getAllForHotel(hotelId);

        // set valid data to roomTypeDtoList
        List<RoomTypeDto> roomTypeDtoList = new ArrayList<>();
        for (RoomType roomType :  roomTypeList) {
            RoomTypeDto roomTypeDto = new RoomTypeDto();
            roomTypeDto.setHotelName(hotelName);
            roomTypeDto.setRoomTypeId(roomType.getId());
            roomTypeDto.setRoomTypeName(roomType.getName());
            roomTypeDto.setSeats(roomType.getSeats());
            roomTypeDto.setRoomTypeId(roomType.getId());
            BigDecimal price =
                    new BigDecimal(String.valueOf(roomType.getPrice()));
            price = price.divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2,
                    BigDecimal.ROUND_HALF_UP);
            roomTypeDto.setPrice(price);
            roomTypeDto.setCurrency(String.valueOf(roomType.getCurrency()));

            roomTypeDtoList.add(roomTypeDto);

        }
        return roomTypeDtoList;
    }
}
