package by.khadasevich.hotel.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class User {
    private long id;
    private String name;
    private String surName;
    private Date birthDate;
    private String email;
    private String password;

}
