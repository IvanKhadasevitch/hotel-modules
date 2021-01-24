package by.khadasevich.hotel.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Admin {
    private long id;
    private long hotelId;
    private String name;
    private String password;

}
