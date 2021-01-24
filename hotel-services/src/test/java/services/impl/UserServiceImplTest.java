package services.impl;

import by.khadasevich.hotel.entities.User;
import org.junit.Assert;
import org.junit.Test;
import by.khadasevich.hotel.services.UserService;
import by.khadasevich.hotel.singleton.SingletonBuilder;
import java.sql.Date;

public class UserServiceImplTest extends Assert {
    private UserService userService = SingletonBuilder.getInstanceImpl(UserService.class);

    @Test
    public void validateEmail(){
        String email = "thisEmail@gmail.com";
        assertTrue(userService.validateEmail(email));

        email = "thisНевалидныйEmail@gmail.com";
        assertFalse(userService.validateEmail(email));
    }

    @Test
    public void getByEmail() {
        User user = userService.get("jordan@gmail.com");
        assertEquals(user.getId(), 1L);
        assertEquals(user.getBirthDate().toLocalDate(),
                Date.valueOf("1980-10-12").toLocalDate());
        assertEquals(user.getEmail(), "jordan@gmail.com");
        assertEquals(user.getName(), "Maikle");
        assertEquals(user.getSurName(), "Jordan");
        assertEquals(user.getPassword(), "1");
    }

    @Test
    public void getById() {
        User user = userService.get(1L);
        assertEquals(user.getId(), 1L);
        assertEquals(user.getBirthDate().toLocalDate(),
                Date.valueOf("1980-10-12").toLocalDate());
        assertEquals(user.getEmail(), "jordan@gmail.com");
        assertEquals(user.getName(), "Maikle");
        assertEquals(user.getSurName(), "Jordan");
        assertEquals(user.getPassword(), "1");
    }

    @Test
    public void existWithEmail() {
        assertTrue(userService.existWithEmail("jordan@gmail.com"));
        assertFalse(userService.existWithEmail("Чижик чижик, где ты был?"));
    }

    @Test
    public  void make() {
        User user = userService.make("Vasja", "Pypkin",
                Date.valueOf("1991-11-01"), "pypkin@tut.by");
        assertNotNull(user);
        assertEquals(user.getSurName(), "Pypkin");
        assertEquals(user.getName(), "Vasja");
        assertEquals(user.getBirthDate(), Date.valueOf("1991-11-01"));
        assertEquals(user.getEmail(), "pypkin@tut.by");
    }
}
