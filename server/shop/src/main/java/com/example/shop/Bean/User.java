package com.example.shop.Bean;

import lombok.Data;

import java.util.Date;

@Data
public class User {
     String username;
     String password;
     String firsttime;
     Date lasttime;
    Float money;

}
