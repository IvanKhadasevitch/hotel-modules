package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.services.UserService;
import by.khadasevich.hotel.dao.UserDao;
import by.khadasevich.hotel.entities.User;
import by.khadasevich.hotel.services.ServiceException;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserServiceImpl
        extends AbstractService implements UserService {

    /**
     * Pattern for email validation.
     */
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$",
              Pattern.CASE_INSENSITIVE);

    /**
     * UserDao instance as singleton.
     */
    private final UserDao userDao =
            SingletonBuilder.getInstanceImpl(UserDao.class);

    private UserServiceImpl() {

    }

    /**
     * Validate email.
     * @param emailStr is email to validation
     * @return true if email valid, otherwise false
     */
    @Override
    public  boolean validateEmail(final String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    /**
     * Get User from database by email service.
     * @param email is User email
     * @return User entity
     */
    @Override
    public User get(final String email) {
        try {
            return userDao.getUserByEmail(email);
        } catch (SQLException e) {
            throw new ServiceException("Error getting User by email " + email);
        }
    }

    /**
     * Get User from database by id service.
     * @param userId is User id
     * @return User entity
     */
    @Override
    public User get(final Serializable userId) {
        try {
            return userDao.get(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting User by id " + userId);
        }
    }

    /**
     * Check if exist User with definite email in database service.
     * @param email is User email
     * @return true if User with definite email exist in database,
     * otherwise false
     */
    @Override
    public boolean existWithEmail(final String email) {
        try {
            return userDao.existWithEmail(email);
        } catch (SQLException e) {
            throw new ServiceException("Error checking exist User with email "
                    + email);
        }
    }

    /**
     * Create User entity with definite parameters service.
     * @param name is User name
     * @param surName is User surname
     * @param birthDate is User birthday
     * @param email is User email
     * @return User entity
     */
    @Override
    public User make(final String name, final String surName,
                     final Date birthDate, final String email) {
        User user = new User();
        user.setName(name);
        user.setSurName(surName);
        user.setBirthDate(birthDate);
        user.setEmail(email);

        return user;
    }

    /**
     * Save User in database service.
     * @param user is User to save
     * @return saved User entity with id
     */
    @Override
    public User save(final User user) {
        try {
            return userDao.save(user);
        } catch (SQLException e) {
            throw new ServiceException("Error saving User " + user);
        }
    }
}
