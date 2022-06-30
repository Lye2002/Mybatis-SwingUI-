package com.example.shop.Bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User_person implements Serializable {
    private String username;
    private String likeshopid;
    private String addtime;
    private List<Shops_detail> Userlike;
}
