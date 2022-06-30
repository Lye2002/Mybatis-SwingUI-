package com.example.shop.Dao;

import com.example.shop.Bean.Shops_detail;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface shop_detail {
    @Select("select * from shop_detail")
    public List<Shops_detail> getAllshops();

    @Select("select * from shop_detail where name like concat('%',#{name},'%') ")
    public List<Shops_detail> getshopsByName(String name);

    @Select("select * from shop_detail where price between #{low} and #{heigh}")
    public List<Shops_detail> getshopsByPriceBetwwen(Integer low, Integer heigh);

    @Select("select * from shop_detail where id=#{id}")
    public Shops_detail getshopsById(String id);

    @Update("update shop_detail set name=#{name},price=#{price},img=#{img},firsttime=#{updatetime},content=#{content} where id=#{id}")
    public int updateshopById(Shops_detail s);

    @Delete("delete from shop_detail where id=#{id}")
    public int deleteShopById(int id);

    @Insert("INSERT INTO shop_detail(name,price,img,firsttime,updatetime,content,sellerid) VALUES (#{name},#{price},#{img},#{firsttime},#{updatetime},#{content},#{sellerid})")
    public int insertShop(Shops_detail s);

//LIMIT[位置偏移量,]行数
    @Select("select * from shop_detail limit #{pageNum},#{Size}")
    public List<Shops_detail> fpage(@PathVariable int pageNum,@PathVariable int Size);

    @Delete("delete from orders where shopid=#{shopid}")
    public Integer delOrderByShopId(String orderid);


}
