package by.khadasevich.hotel.dto.mappers.impl;

import by.khadasevich.hotel.dto.OrderDto;
import by.khadasevich.hotel.dto.mappers.OrderDtoMapper;
import by.khadasevich.hotel.entities.*;
import by.khadasevich.hotel.services.HotelService;
import by.khadasevich.hotel.services.RoomService;
import by.khadasevich.hotel.services.RoomTypeService;
import by.khadasevich.hotel.services.UserService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class OrderDtoMapperImpl implements OrderDtoMapper {
    /**
     * HotelService singleton instance.
     */
    private final HotelService hotelService =
            SingletonBuilder.getInstanceImpl(HotelService.class);
    /**
     * RoomTypeService singleton instance.
     */
    private final RoomTypeService roomTypeService =
            SingletonBuilder.getInstanceImpl(RoomTypeService.class);
    /**
     * RoomService singleton instance.
     */
    private final RoomService roomService =
            SingletonBuilder.getInstanceImpl(RoomService.class);
    /**
     * UserService singleton instance.
     */
    private final UserService userService =
            SingletonBuilder.getInstanceImpl(UserService.class);

    /**
     * Takes the fields with Id from the order.
     * Fills them with values from the DB, and creates OrderDto
     * @param order is Order to map
     * @return instance of OrderDto
     */
    @Override
    public OrderDto map(final Order order) {
        // validate data from DB
        final long DEFAULT_ID = -1L;
        final String NOT_DEFINE = "NotDefine";

        RoomType roomType = roomTypeService.get(order.getRoomTypeId());
        long hotelId;
        String roomTypeName;
        int roomSeats;
        int roomPrice;
        String currency;
        if (roomType != null) {
            hotelId = roomType.getHotelId();
            roomTypeName = roomType.getName();
            roomSeats = roomType.getSeats();
            roomPrice = roomType.getPrice();
            currency = String.valueOf(roomType.getCurrency());
        } else {
            hotelId = DEFAULT_ID;
            roomTypeName = NOT_DEFINE;
            roomSeats = -1;
            roomPrice = 0;
            currency = NOT_DEFINE;
        }
        BigDecimal price = new BigDecimal(String.valueOf(roomPrice));
        price = price.divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2,
                BigDecimal.ROUND_HALF_UP);

        Hotel hotel = hotelService.get(hotelId);
        String hotelName = hotel != null
                ? hotel.getName()
                : NOT_DEFINE;
        Room room = roomService.get(order.getRoomId());
        String roomNumber = room != null
                ? room.getNumber()
                : NOT_DEFINE;
        User user = userService.get(order.getUserId());
        String guestName = user != null
                ? user.getName() + " " + user.getSurName()
                : NOT_DEFINE;
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        long periodDays = order.getArrivalDate().toLocalDate()
                               .until(order.getEventsDate().toLocalDate(),
                                       ChronoUnit.DAYS);
        BigDecimal total = price.multiply(BigDecimal.valueOf(periodDays));

        // make orderDto & set valid data to orderDto
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setDateProcessing(order.getDateProcessing());
        orderDto.setHotelId(hotelId);
        orderDto.setHotelName(hotelName);
        orderDto.setRoomTypeName(roomTypeName);
        orderDto.setRoomNumber(roomNumber);
        orderDto.setSeats(roomSeats);
        orderDto.setPrice(price);
        orderDto.setGuestName(guestName);
        orderDto.setArrivalDate(order.getArrivalDate());
        orderDto.setEventsDate(order.getEventsDate());

        Date currentDate = new Date((new java.util.Date()).getTime());
        orderDto.setExpired(currentDate.after(order.getArrivalDate()));

        orderDto.setStatus(order.getStatus());
        orderDto.setTotal(total);
        orderDto.setCurrency(currency);
        orderDto.setBillId(order.getBillId());

        return orderDto;
    }
}
