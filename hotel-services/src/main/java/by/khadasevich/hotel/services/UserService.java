package by.khadasevich.hotel.services;

import by.khadasevich.hotel.entities.User;

import java.io.Serializable;
import java.sql.Date;

public interface UserService {
    /**
     * Validate email.
     * @param emailStr is email to validation
     * @return true if email valid, otherwise false
     */
    boolean validateEmail(String emailStr);

    /**
     * Get User from database by email service.
     * @param email is User email
     * @return User entity
     */
    User get(String email);

    /**
     * Get User from database by id service.
     * @param userId is User id
     * @return User entity
     */
    User get(Serializable userId);

    /**
     * Check if exist User with definite email in database service.
     * @param email is User email
     * @return true if User with definite email exist in database,
     * otherwise false
     */
    boolean existWithEmail(String email);

    /**
     * Create User entity with definite parameters service.
     * @param name is User name
     * @param surName is User surname
     * @param birthDate is User birthday
     * @param email is User email
     * @return User entity
     */
    User make(String name, String surName, Date birthDate, String email);

    /**
     * Save User in database service.
     * @param user is User to save
     * @return saved User entity with id
     */
    User save(User user);
}
