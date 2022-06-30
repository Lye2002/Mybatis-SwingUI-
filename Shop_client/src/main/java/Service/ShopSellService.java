package Service;

import Bean.Goods;
import Dao.ShopSell_Dao;
import Dao.User_Dao;
import config.Mybatis;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShopSellService {
    public static ShopSell_Dao shopSell_dao= Mybatis.getMapper(ShopSell_Dao.class);

    public static List<Goods> getShopsGood(String sellerid){
        System.out.println(shopSell_dao.getSellGoods(sellerid));
        return shopSell_dao.getSellGoods(sellerid);
    }

    public static Boolean updateGood(String name,Float price, String img,String content, String sellerid,String id){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        java.util.Date date = new Date();//获得当前时间
        String birthday = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
        return shopSell_dao.updateGood(name,price,img,birthday,content,sellerid,id);
    }

    public static Integer delGood(String id,String sellerid){
        OrderService.delOrderByShopid(id);
        return shopSell_dao.delGood(id, sellerid);
    }

    public static List<Goods> getSellGoods(String goodName, String sellerid){
        return shopSell_dao.getSellGoodsBynameandId(goodName, sellerid);
    }

    public static Integer addGood(String name,Float price,String img,String firsttime,String content,String sellerid){
        return shopSell_dao.addGood(name, price, img, firsttime, content, sellerid);
    }



    @Test
    public void test(){
        System.out.println(ShopSellService.getSellGoods("2", "2"));
        System.out.println(ShopSellService.getShopsGood("2"));
        System.out.println(ShopSellService.delGood("32", "2"));
    }

}
