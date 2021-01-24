package by.khadasevich.hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import by.khadasevich.hotel.entities.enums.BillStatusType;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
public class BillDto {
    private  long id;
    private  long orderId;
    private  long userId;
    private  String guestName;
    private  long hotelId;
    private  String hotelName;
    private  long roomId;
    private  String roomTypeName;
    private String roomNumber;
    private Date arrivalDate;
    private Date eventsDate;
    private BigDecimal price;
    private BigDecimal total;
    private String currency;
    private BillStatusType status;
}
