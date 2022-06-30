package com.example.shop.Bean;

import lombok.Data;

@Data
public class Login {
    Boolean flag;
    String username;

    public Login(boolean b, String username) {
        this.flag=b;
        this.username=username;
    }
}
