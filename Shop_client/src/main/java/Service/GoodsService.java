package Service;

import Bean.Goods;
import Dao.Goods_AllDao;
import config.Mybatis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public  class  GoodsService {
    private static InputStream in;

//    static {
//        try {
//            in = Resources.getResourceAsStream("mybatis-Config.xml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    ;
//    private static SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
//    private static SqlSession session=factory.openSession();
    private static Goods_AllDao goods_allDao= Mybatis.getMapper(Goods_AllDao.class);


    public static List<Goods> getAllGoods() throws Exception {

//        in = Resources.getResourceAsStream("mybatis-Config.xml");
//        factory = new SqlSessionFactoryBuilder().build(in);
//        session = factory.openSession();
       // goods_allDao = session.getMapper(Goods_AllDao.class);


        List<Goods> goods = goods_allDao.getAllShops();

        return goods;
    }

    public static List<Goods> pageShops(Integer pageindex,Integer total){
        List<Goods> pgoods =goods_allDao.pageShops(pageindex*total,total);
        System.out.println(pgoods);
        return pgoods;
    }

    public static List<Goods> searchLikeGoodsByName(String name){
        List<Goods> LikeGodds=goods_allDao.getshopsLikeName(name);
        System.out.println(LikeGodds);
        return LikeGodds;
    }


    @Test
    public void t(){
        System.out.println(GoodsService.pageShops(0, 10));
    }
}
