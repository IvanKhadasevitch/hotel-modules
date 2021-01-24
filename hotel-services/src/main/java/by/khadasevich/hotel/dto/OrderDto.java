package by.khadasevich.hotel.dto;

import by.khadasevich.hotel.entities.enums.OrderStatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
public class OrderDto {

    private long id;
    private  Date dateProcessing;
    private long hotelId;
    private String hotelName;
    private String roomTypeName;
    private String roomNumber;
    private int seats;
    private BigDecimal price;
    private String guestName;
    private Date arrivalDate;
    private Date eventsDate;
    private boolean expired;
    private OrderStatusType status;
    private BigDecimal total;
    private String currency;
    private  long billId;

//    public static void main(String[] args) {
//        Calendar calendar1 = new GregorianCalendar(2017, 1, 1);
//        Calendar calendar2 = new GregorianCalendar(2018, 1, 1);
//        Calendar today = Calendar.getInstance();
//        java.sql.Date dateSQL1 = new java.sql.Date(calendar1.getTime().getTime()); // time in milliseconds
//        java.sql.Date dateSQL2 = new java.sql.Date(calendar2.getTime().getTime());
//        LocalDate lodDate1 = dateSQL1.toLocalDate();
//        LocalDate lodDate2 = dateSQL2.toLocalDate();
//        long dayPeriod = lodDate1.until(lodDate2, ChronoUnit.DAYS); // this right period in days!!!
//        System.out.println("days between dates: " + dayPeriod);
//    }

}
