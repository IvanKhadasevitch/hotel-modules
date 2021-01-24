package by.khadasevich.hotel.services;

import by.khadasevich.hotel.entities.Admin;

import java.io.Serializable;

public interface AdminService {
    /**
     * Get Admin from database by id.
     * @param adminId is Admin id
     * @return Admin entity
     */
    Admin get(Serializable adminId);
}
