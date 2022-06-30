package Dao;

import Bean.Goods;
import Bean.UserLogin;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ShopSell_Dao {
    @Select("select * from shop_detail where sellerid=#{sellerid}")
    public List<Goods> getSellGoods(@Param("sellerid") String sellerid);

    @Select("select * from shop_detail where sellerid=#{sellerid} and name like concat('%',#{goodName},'%')")
    public List<Goods> getSellGoodsBynameandId(@Param("goodName") String goodName,@Param("sellerid") String sellerid);

    @Delete("delete from shop_detail where id=#{id} and sellerid=#{sellerid}")
    public Integer delGood(@Param("id")String id,@Param("sellerid")String sellerid);

    @Insert("insert into shop_detail(name,price,img,firsttime,content,sellerid) values (#{name},#{price},#{img},#{firsttime},#{content},#{sellerid})")
    public Integer addGood(@Param("name")String name,@Param("price")Float price,@Param("img")String img,@Param("firsttime")String firsttime,@Param("content")String content,@Param("sellerid")String sellerid);

    @Update("update shop_detail set name=#{name},price=#{price},img=#{img},firsttime=#{firsttime},content=#{content} where sellerid=#{sellerid} and id=#{id}")
    public Boolean updateGood(@Param("name")String name,@Param("price")Float price,@Param("img")String img,@Param("firsttime")String firsttime,@Param("content")String content,@Param("sellerid")String sellerid,@Param("id")String id);

    @Select("select username,password from userlogin where username=#{username} and password=#{password}")
    public UserLogin userLogin(@Param("username") String username, @Param("password") String password);
}
