package com.example.shop.Service;


import com.example.shop.Bean.User;
import com.example.shop.Dao.Userlogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdmin implements Userlogin {
    @Autowired
    private Userlogin userlogin;

    @Override
    public List<User> getAllUser() {
        return userlogin.getAllUser();
    }

    @Override
    public List<User> Login(String username, String password) {
        return userlogin.Login(username, password);
    }

    @Override
    public Boolean Register(User user) {
        return userlogin.Register(user);
    }

    @Override
    public Boolean DeleteUser(String username) {
        return userlogin.DeleteUser(username);
    }

    @Override
    public Boolean changeInfo(User user) {
        return userlogin.changeInfo(user);
    }

    public User getUserById(String username){
        return userlogin.getUserById(username);
    }
}
