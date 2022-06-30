package com.example.shop.Bean;

import lombok.Data;

import java.util.Date;

@Data
public class Shops_detail {
    private String id;
    private String name;
    private float price;
    private String img;
    private String firsttime;
    private Date updatetime;
    private String content;
    private String sellerid;
}
