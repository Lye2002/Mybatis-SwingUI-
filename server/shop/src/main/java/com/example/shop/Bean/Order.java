package com.example.shop.Bean;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String orderid;
    private String shopid;
    private String buytime;
    private String username;
    private String address;
    private String sellerid;
    private Shops_detail shops_detail;
}
