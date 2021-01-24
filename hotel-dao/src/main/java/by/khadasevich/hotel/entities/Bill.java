package by.khadasevich.hotel.entities;

import by.khadasevich.hotel.entities.enums.BillStatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
public class Bill {

    private long id;
    private Date date;
    private long orderId;
    private long userId;
    private long roomId;
    private Date arrivalDate;
    private Date eventsDate;
    private long total;
    private BillStatusType status;

}
