package com.example.shop.Service;


import com.example.shop.Bean.Order;
import com.example.shop.Dao.Orders_Dao;
import com.example.shop.Dao.User_personDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.List;

@Service
public class Orders_Service implements Orders_Dao{

    @Qualifier("orders_Dao")
    @Autowired
    public Orders_Dao orders_dao;

    @Override
    public List<Order> getAllOrders() {
        return orders_dao.getAllOrders();
    }

    @Override
    public Integer delOrder(String orderid) {
        return orders_dao.delOrder(orderid);
    }

    @Override
    public Integer updateOrderInfo(Order order) {
        return orders_dao.updateOrderInfo(order);
    }

    @Override
    public Integer delOrderByShopid(String shopid) {
        return orders_dao.delOrderByShopid(shopid);
    }
}
