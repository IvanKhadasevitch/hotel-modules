package by.khadasevich.hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RoomTypeDto {
    private String hotelName;
    private long roomTypeId;
    private String roomTypeName;
    private int seats;
    private BigDecimal price;
    private String currency;
}
