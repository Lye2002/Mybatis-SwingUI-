package Service;

import Bean.Goods;
import Dao.Goods_AllDao;
import config.Mybatis;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataDao {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private Goods_AllDao goods_allDao;


    @Before
    public void init() throws Exception{
//        in = Resources.getResourceAsStream("mybatis-Config.xml");
//        factory = new SqlSessionFactoryBuilder().build(in);
//        session = factory.openSession();
//        goods_allDao = session.getMapper(Goods_AllDao.class);
        goods_allDao=Mybatis.getMapper(Goods_AllDao.class);
    }

    @After
    public void destory() throws Exception{
        session.commit();
        session.close();
        in.close();
    }

    @Test
    public void testFindAll() throws IOException {
        in = Resources.getResourceAsStream("mybatis-Config.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();

        goods_allDao = session.getMapper(Goods_AllDao.class);
        List<Goods> users = goods_allDao.getAllShops();
        System.out.println(users);

//        for (Goods user : users) {
//            System.out.println("-----每个用户的信息");
//            System.out.println(user);
//
//        }
    }


}
