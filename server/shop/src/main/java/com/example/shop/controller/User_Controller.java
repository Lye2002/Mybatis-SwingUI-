package com.example.shop.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.shop.Bean.Login;
import com.example.shop.Bean.User;
import com.example.shop.Service.Shop_detailService;
import com.example.shop.Service.UserAdmin;
import com.example.shop.controller.config.R;
import com.example.shop.controller.config.getNow_Time;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class User_Controller {
    @Autowired
    private UserAdmin userAdmin;

    @PostMapping("/login")
    public JSON login(@RequestBody User user){
        System.out.println(user);
        List<User> login = userAdmin.Login(user.getUsername(), user.getPassword());
        System.out.println(login);
        if(!login.isEmpty()){
            Login r=new Login(true,user.getUsername());
            //System.out.println("---------------------");
            System.out.println(JSON.toJSON(r));
              //JSON.toJSON(r);
            return (JSON) JSON.toJSON(r);
        }else {
            Login r = new Login(false, user.getUsername());
            return (JSON) JSON.toJSON(r);
        }


    }
    @GetMapping("/getAllUser")
    public R getAllUser(){
        return new R(true,userAdmin.getAllUser());
    }

    @GetMapping("/getUserById/{id}")
    public R getUserById(@PathVariable String id){
        System.out.println(id);
        return new R(true,userAdmin.getUserById(id));
    }

    @PutMapping("/editUser")
    public R updateUserInfo(@RequestBody User user){
        user.setFirsttime(getNow_Time.getNowTime());
        return new R(true,userAdmin.changeInfo(user));
    }

    @DeleteMapping("{username}")
    public R delUser(@PathVariable String username){
        return new R(true,userAdmin.DeleteUser(username));
    }

    @PostMapping
    public R addUser(@RequestBody User user){
        //System.out.println(user);
        user.setFirsttime(getNow_Time.getNowTime());
        return new R(true,userAdmin.Register(user));
    }




}
