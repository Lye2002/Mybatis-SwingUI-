package com.example.shop.controller;


import com.example.shop.Bean.Order;
import com.example.shop.Service.Orders_Service;
import com.example.shop.Service.Shop_detailService;
import com.example.shop.controller.config.R;
import org.apache.ibatis.annotations.Delete;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders_admin")
public class Order_Controller {
    @Autowired
    public Orders_Service orders_service;

    @GetMapping
    public R getAllOrders(){
        System.out.println(orders_service.getAllOrders());
        return new R(true,orders_service.getAllOrders());
    }

    @DeleteMapping("{orderid}")
    public R delOrder(@PathVariable String orderid){
        return new R(true,orders_service.delOrder(orderid));
    }

    @PutMapping
    public R updateOrder(@RequestBody Order order){
        return new R(true,orders_service.updateOrderInfo(order));
    }




}
