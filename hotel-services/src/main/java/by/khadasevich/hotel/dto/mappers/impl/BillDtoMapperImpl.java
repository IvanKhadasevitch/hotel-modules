package by.khadasevich.hotel.dto.mappers.impl;

import by.khadasevich.hotel.dto.mappers.BillDtoMapper;
import by.khadasevich.hotel.dto.BillDto;
import by.khadasevich.hotel.entities.*;
import by.khadasevich.hotel.services.HotelService;
import by.khadasevich.hotel.services.RoomService;
import by.khadasevich.hotel.services.RoomTypeService;
import by.khadasevich.hotel.services.UserService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.math.BigDecimal;

public class BillDtoMapperImpl implements BillDtoMapper {

    /**
     * UserService singleton instance.
     */
    private final UserService userService =
            SingletonBuilder.getInstanceImpl(UserService.class);
    /**
     * RoomService singleton instance.
     */
    private final RoomService roomService =
            SingletonBuilder.getInstanceImpl(RoomService.class);
    /**
     * RoomTypeService singleton instance.
     */
    private final RoomTypeService roomTypeService =
            SingletonBuilder.getInstanceImpl(RoomTypeService.class);
    /**
     * HotelService singleton instance.
     */
    private final HotelService hotelService =
            SingletonBuilder.getInstanceImpl(HotelService.class);

    /**
     * Takes the fields from the bill with Id.
     * Fills them with values from the DB, and creates BillDto
     * @param bill is Bill
     * @return instance of BillDto
     */
    @Override
    public BillDto map(final Bill bill) {
        //take and validate data from DB
        final long DEFAULT_ID = -1L;
        String NOT_DEFINE = "NotDefine";

        User user = userService.get(bill.getUserId());
        String guestName = user != null
                ? user.getName() + " " + user.getSurName()
                : NOT_DEFINE;
        String roomNumber;
        long roomTypeId;
        Room room = roomService.get(bill.getRoomId());
        if (room != null) {
            roomTypeId = room.getRoomTypeId();
            roomNumber = room.getNumber();
        } else {
            roomTypeId = DEFAULT_ID;
            roomNumber = NOT_DEFINE;
        }
        RoomType roomType = roomTypeService.get(roomTypeId);
        String roomTypeName;
        int roomPrice;
        long hotelId;
        String currency;
        if (roomType != null) {
            roomTypeName = roomType.getName();
            roomPrice = roomType.getPrice();
            hotelId = roomType.getHotelId();
            currency = String.valueOf(roomType.getCurrency());
        } else {
            roomTypeName = NOT_DEFINE;
            roomPrice = 0;
            hotelId = DEFAULT_ID;
            currency = NOT_DEFINE;
        }
        BigDecimal price = new BigDecimal(String.valueOf(roomPrice));
        price = price.divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2,
                BigDecimal.ROUND_HALF_UP);

        BigDecimal total = new BigDecimal(String.valueOf(bill.getTotal()));
        total = total.divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2,
                BigDecimal.ROUND_HALF_UP);

        Hotel hotel = hotelService.get(hotelId);
        String hotelName = hotel != null
                ? hotel.getName()
                : NOT_DEFINE;

        BillDto billDto = new BillDto();

        // set valid data to billDto
        billDto.setId(bill.getId());
        billDto.setOrderId(bill.getOrderId());
        billDto.setUserId(bill.getUserId());
        billDto.setGuestName(guestName);
        billDto.setHotelId(hotelId);
        billDto.setHotelName(hotelName);
        billDto.setRoomId(bill.getRoomId());
        billDto.setRoomTypeName(roomTypeName);
        billDto.setRoomNumber(roomNumber);
        billDto.setArrivalDate(bill.getArrivalDate());
        billDto.setEventsDate(bill.getEventsDate());
        billDto.setPrice(price);
        billDto.setTotal(total);
        billDto.setCurrency(currency);
        billDto.setStatus(bill.getStatus());

        return billDto;

    }
}
