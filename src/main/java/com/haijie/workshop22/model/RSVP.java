package com.haijie.workshop22.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RSVP {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Date confirmationDate;
    private String comments;
}
