package Service;

import Bean.Order;
import Bean.User_person;
import Dao.Order_Dao;
import config.Mybatis;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderService {
//    private static InputStream in;
//
//    static {
//        try {
//            in = Resources.getResourceAsStream("mybatis-Config.xml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private static SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
//    private static SqlSession session=factory.openSession();
//     private static Order_Dao order_dao= session.getMapper(Order_Dao.class);
    private static Order_Dao order_dao=Mybatis.getMapper(Order_Dao.class);

    public static List<Order> getUserOrders(String username){
        return order_dao.getUserOrder(username);
    }

    public static List<Order> getSellerOrders(String username) {
        return order_dao.getSellerOrders(username);
    }

        public static Boolean delOrderUser(String username,String orderid){
        return order_dao.delOrderUser(username, orderid);
    }

    public static Integer buyAshop(Integer shopid, String buytime, String username, String address,String sellerid){
        return order_dao.buyAshop(shopid,buytime,username,address,sellerid);
    }

    public static Integer delOrder(String orderid){
        return order_dao.delOrder(orderid);
    }

    public static Integer updateAddress(String address,String orderid){
        return order_dao.updateAddress(address, orderid);
    }


    public static Integer delOrderByShopid(String shopid){
        return order_dao.delOrderByShopid(shopid);
    }


    @Test
    public void a(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        java.util.Date date = new Date();//获得当前时间
        String birthday = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
        OrderService.buyAshop(1,birthday,"1132","广州","2");
    }
    @Test
    public void b(){
        System.out.println(order_dao.getUserOrder("111"));
    }





}
