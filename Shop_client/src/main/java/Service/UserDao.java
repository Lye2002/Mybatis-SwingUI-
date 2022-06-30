package Service;

import Bean.UserLikeShops;
import Bean.UserLogin;
import Dao.User_Dao;
import config.Mybatis;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDao {
    public static User_Dao user_dao=Mybatis.getMapper(User_Dao.class);

    public static UserLogin findUser(String username,String password){
        return user_dao.userLogin(username,password);
    }


    public static Boolean changePassword(String username,String password){
      return  user_dao.changePassword(username,password);
    }

    public static int addUser(String username,String password){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        java.util.Date date = new Date();//获得当前时间
        String birthday = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
        return user_dao.addUser(username, password, birthday);
    }

    public static Float getMoney(String useranme){
        return user_dao.userMoney(useranme);
    }

    public static Boolean updateMoney(Float money,String username){
       return user_dao.updateMoney(money,username);
    }
    
    @Test
    public void tt(){
        System.out.println(UserDao.findUser("111", "00"));
        System.out.println(UserDao.findUser("222", "222"));
    }



//    public static void main(String[] args) {
//       System.out.println(UserDao.findUser("111", "00"));
//        System.out.println(UserDao.getMoney("111"));
//        System.out.println(UserDao.updateMoney(200.0F, "111"));
//        // System.out.println(UserDao.changePassword("111", "111"));
//    }
}
