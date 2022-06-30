package com.example.shop.controller;


import com.example.shop.Bean.Shops_detail;
import com.example.shop.Service.Orders_Service;
import com.example.shop.Service.Shop_detailService;
import com.example.shop.controller.config.R;
import com.example.shop.controller.config.getNow_Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/shop_admin")
public class Shop_detailController {
    @Autowired
    private Shop_detailService shop_detailService;
    private Orders_Service orders_service;

    @GetMapping("/getAllshops")
    public R getAllshops(){
        return new R(true,shop_detailService.getAllshops());
    }

    @DeleteMapping("{id}")
    public R delByIdshop(@PathVariable Integer id){
        //orders_service.delOrderByShopid(String.valueOf(id));
        return new R(true,shop_detailService.deleteShopById(id));
    }

    @PostMapping
    public R insertShop(@RequestBody Shops_detail shops_detail){
        String date=getNow_Time.getNowTime();
        shops_detail.setFirsttime(date);
        return new R(true,shop_detailService.insertShop(shops_detail));
    }

    @PutMapping
    public R updateShop(@RequestBody Shops_detail shops_detail){
        String date=getNow_Time.getNowTime();
        shops_detail.setFirsttime(date);
        return new R(true,shop_detailService.updateshopById(shops_detail));
    }

    @GetMapping("{id}")
    public R getShopById(@PathVariable String id){
        return new R(true,shop_detailService.getshopsById(id));
    }

    @GetMapping("/getSim/{name}")
    public R getshopsByName(@PathVariable String name){return new R(true,shop_detailService.getshopsByName(name));}

    @GetMapping("/fpage/{pageNum}/{size}")
    public R getFpage(@PathVariable int pageNum,@PathVariable int size){
        return new R(true,shop_detailService.fpage(pageNum,size));
    }



}
