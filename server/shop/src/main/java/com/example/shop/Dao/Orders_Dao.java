package com.example.shop.Dao;

import com.example.shop.Bean.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Orders_Dao {
    @Select("select * from orders as o join shop_detail as s on o.shopid=s.id")
    @Results(id="accountMap",value = {
            @Result(id = true,column = "orderid",property = "orderid"),
            @Result(column = "shopid",property = "shopid"),
            @Result(column = "username",property = "username"),
            @Result(column = "sellerid",property = "sellerid"),
            @Result(property = "shops_detail",column = "shopid",one =
            @One(select="com.example.shop.Dao.shop_detail.getshopsById"))
    })
    public List<Order> getAllOrders();


    @Delete("delete from orders where orderid=#{orderid}")
    public Integer delOrder(String orderid);

    @Delete("delete from orders where shopid=#{shopid}")
    public Integer delOrderByShopid(String shopid);

    @Update("update orders set shopid=#{shopid},username=#{username},address=#{address},sellerid=#{sellerid} where orderid=#{orderid}")
    public Integer updateOrderInfo(Order order);







}
