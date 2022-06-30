package config;

import Dao.Goods_AllDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Mybatis {
    public static InputStream in;
    static {
        try {
            in = Resources.getResourceAsStream("mybatis-Config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
    public static SqlSession session=factory.openSession(true);

    public static <T> T getMapper(Class<T> DaoClassName){
        Class name=DaoClassName;
        T t=session.getMapper(DaoClassName);
        return t;
    }

    public static void main(String[] args) {
        System.out.println(Mybatis.getMapper(Goods_AllDao.class));
    }

}
