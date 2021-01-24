package by.khadasevich.hotel.dto.mappers;

import by.khadasevich.hotel.dto.BillDto;
import by.khadasevich.hotel.entities.Bill;

public interface BillDtoMapper {

    /**
     * Takes the fields from the bill with Id,
     * fills them with values from the DB, and creates BillDto
     * @param bill is Bill
     * @return instance of BillDto
     */
    BillDto map(Bill bill);
}
