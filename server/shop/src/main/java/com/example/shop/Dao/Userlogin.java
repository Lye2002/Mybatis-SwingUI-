package com.example.shop.Dao;

import com.example.shop.Bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface Userlogin {
    @Select("select * from userlogin")
    public List<User> getAllUser();

    @Select("select * from adminlogin where username=#{username} and password=#{password}")
    public List<User> Login(@Param("username") String username,@Param("password") String password);

    @Insert("insert into userlogin(username,password,firsttime,lasttime,money) values(#{username},#{password},#{firsttime},#{lasttime},#{money}) ")
    public Boolean Register(User user);

    @Delete("delete from userlogin where username=#{username}")
    public Boolean DeleteUser(String username);

    @Update("update userlogin set password=#{password},firsttime=#{firsttime},money=#{money} where username=#{username}")
    //public Boolean changeInfo(String password,String username,String firsttime,Float money);
    public Boolean changeInfo(User user);

    @Select("select * from userlogin where username=#{username}")
    public User getUserById(String username);



}
