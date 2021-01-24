package by.khadasevich.hotel.dto.mappers;

import by.khadasevich.hotel.dto.RoomTypeDto;

import java.io.Serializable;
import java.util.List;

public interface RoomTypeDtoMapper {

    /**
     * Map All roomType for Hotel with hotelId.
     * Add hotel name and return List of RoomTypeDto
     * @param hotelId is Hotel id
     * @return List of RoomTypeDto for definite Hotel
     */
    List<RoomTypeDto> mapAll(Serializable hotelId);
}
