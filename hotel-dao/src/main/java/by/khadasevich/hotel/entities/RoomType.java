package by.khadasevich.hotel.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import by.khadasevich.hotel.entities.enums.CurrencyType;

@Data
@NoArgsConstructor
public class RoomType {
    private long id;
    private String name;
    private int seats;
    private int price;
    private CurrencyType currency;
    private long hotelId;

}
