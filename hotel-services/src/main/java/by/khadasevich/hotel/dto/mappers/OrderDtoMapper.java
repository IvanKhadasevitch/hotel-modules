package by.khadasevich.hotel.dto.mappers;

import by.khadasevich.hotel.dto.OrderDto;
import by.khadasevich.hotel.entities.Order;

public interface OrderDtoMapper {

    /**
     * Takes the fields with Id from the order.
     * Fills them with values from the DB, and creates OrderDto
     * @param order is Order to map
     * @return instance of OrderDto
     */
    OrderDto map(Order order);
}
