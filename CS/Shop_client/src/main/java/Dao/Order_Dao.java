package Dao;

import Bean.Goods;
import Bean.Order;
import Bean.User_person;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

public interface Order_Dao {

    @Delete("delete from orders where orderid=#{orderid} and username=#{username}")
    public Boolean delOrderUser(@Param("username") String username,@Param("orderid")String orderid);


    @Select("select * from shop_detail join orders  on shop_detail.id=orders.shopid where username=#{username}")
    @Results({
            @Result(id=true,property = "orderid",column = "orderid"),
            @Result(property = "shopid",column = "shopid"),
            @Result(property = "buytime",column = "buytime"),
            @Result(property = "address",column = "address"),//property 对应实体类中的对象名称  column 对应数据库中搜索到的字段
            @Result(property = "goods",column = "shopid",one = @One(select = "Dao.Goods_AllDao.getShopsByid"))
    })
    public List<Order> getUserOrder(String username);


    @Results({
            @Result(id=true,property = "orderid",column = "orderid"),
            @Result(property = "shopid",column = "shopid"),
            @Result(property = "buytime",column = "buytime"),
            @Result(property = "address",column = "address"),
            @Result(property = "goods",column = "shopid",one = @One(select = "Dao.Goods_AllDao.getShopsByid"))
    })
    @Select("select * from shop_detail join orders  on shop_detail.id=orders.shopid where orders.sellerid=#{sellerid}")
    public List<Order> getSellerOrders(String sellerid);

    @Insert("insert into orders(shopid,buytime,username,address,sellerid) values(#{shopid},#{buytime},#{username},#{address},#{sellerid})")
    public Integer buyAshop(@Param("shopid") Integer shopid,@Param("buytime") String buytime,@Param("username") String username,@Param("address") String address,@Param("sellerid")String sellerid);

    @Delete("delete from orders where orderid=#{orderid}")
    public Integer delOrder(@Param("orderid") String orderid);

    @Update("update orders set address=#{address} where orderid=#{orderid}")
    public Integer updateAddress(@Param("address")String address,@Param("orderid")String orderid);

    @Delete("delete from orders where shopid=#{shopid}")
    public Integer delOrderByShopid(@Param("shopid") String shopid);

}
