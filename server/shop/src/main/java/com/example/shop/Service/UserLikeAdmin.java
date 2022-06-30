package com.example.shop.Service;


import com.example.shop.Bean.Shops_detail;
import com.example.shop.Bean.User_person;
import com.example.shop.Dao.User_personDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikeAdmin implements User_personDao {
    @Autowired
    private User_personDao user_personDao;

    @Override
    public List<User_person> getAllUserLikeShops() {
        return user_personDao.getAllUserLikeShops();
    }

    @Override
    public List<User_person> getUserLikeShopsById(String username) {
        return user_personDao.getUserLikeShopsById(username);
    }

    @Override
    public Shops_detail mostLikeshop() {
        return user_personDao.mostLikeshop();
    }

    @Override
    public Boolean delLikeshop(String username, Integer shopLikeid) {
        return user_personDao.delLikeshop(username, shopLikeid);
    }

    @Override
    public Boolean delAllshopById(Integer shoplikeid) {
        return user_personDao.delAllshopById(shoplikeid);
    }
}
