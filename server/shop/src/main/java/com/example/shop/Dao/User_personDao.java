package com.example.shop.Dao;

import com.example.shop.Bean.Shops_detail;
import com.example.shop.Bean.User_person;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface User_personDao {
    @Select("SELECT * FROM shop_detail join userlikeshop as ul on ul.likeshopid=shop_detail.id")
    public List<User_person> getAllUserLikeShops();

    @Select("SELECT * FROM shop_detail join userlikeshop as ul on ul.likeshopid=shop_detail.id where username=#{username}")
    public List<User_person> getUserLikeShopsById(String username);


    //select * from where id=(select likeshopid,count(likeshopid) as counttime from shop_detail join userlikeshop as ul on ul.likeshopid=shop_detail.id GROUP BY likeshopid ORDER BY counttime desc limit 1)
    @Select("select * from shop_detail where id=(select likeshopid as counttime from shop_detail join userlikeshop as ul on ul.likeshopid=shop_detail.id GROUP BY likeshopid ORDER BY counttime desc limit 1)")
    public Shops_detail mostLikeshop();

    @Delete("delete from userlikeshop where username=#{username} and shoplikeid=#{shoplikeid}")
    public Boolean delLikeshop(String username,Integer shopLikeid);

    @Delete("delete from userlikeshop where shoplikeid=#{shoplikeid}")
    public Boolean delAllshopById(Integer shoplikeid);


}
