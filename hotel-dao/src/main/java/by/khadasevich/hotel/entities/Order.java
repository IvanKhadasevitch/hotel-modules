package by.khadasevich.hotel.entities;

import by.khadasevich.hotel.entities.enums.OrderStatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class Order {

    private long id;
    private Date dateProcessing;
    private long adminId;
    private OrderStatusType status;
    private long userId;
    private long roomTypeId;
    private long roomId;
    private Date arrivalDate;
    private Date eventsDate;
    private long total;
    private long billId;
}
