package by.khadasevich.hotel.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Room {
    private long id;
    private String number;
    private long roomTypeId;
}

