package Dao;


import Bean.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface Goods_AllDao {


    @Select("select * from shop_detail")
    public  List<Goods> getAllShops();

    @Select("select * from shop_detail where id=#{id}")
    public List<Goods> getShopsByid(Integer id);


    @Select("select * from shop_detail LIMIT #{pageindex},#{total}")
    public List<Goods> pageShops(@Param("pageindex") Integer pageindex, @Param("total") Integer total);

    @Select("select * from shop_detail where name like concat('%',#{name},'%') ")
    public List<Goods> getshopsLikeName(String name);
}
