package com.example.shop.controller;


import com.example.shop.Bean.Menu;
import com.example.shop.Service.Show_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class Show {
    @Autowired
    public Show_Service show_service;

    @GetMapping("/menu")
    public List<Menu> getMenu(){
        System.out.println(show_service.getMenu());
       return show_service.getMenu();
    }
}
