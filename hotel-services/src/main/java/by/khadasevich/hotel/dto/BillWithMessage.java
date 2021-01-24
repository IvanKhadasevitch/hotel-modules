package by.khadasevich.hotel.dto;

import by.khadasevich.hotel.entities.Bill;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillWithMessage {
    private Bill bill;
    private String message;
}
