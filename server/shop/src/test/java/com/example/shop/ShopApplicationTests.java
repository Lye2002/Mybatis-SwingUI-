package com.example.shop;

import com.example.shop.Dao.User_personDao;
import com.example.shop.Bean.User_person;
import com.example.shop.Dao.shop_detail;
import com.example.shop.Dao.Userlogin;
import com.example.shop.Service.UserAdmin;
import com.example.shop.Service.UserLikeAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {
    @Value("${spring.datasource.url}")
    private String x;


    @Qualifier("shop_detail")
    @Autowired
    private shop_detail bookDao;

 //   @Qualifier("User_personDao")

    //@Qualifier("User_person")
    @Qualifier("user_personDao")
    @Autowired
    private User_personDao u;

    @Autowired
    private Userlogin userlogin;

    @Autowired
    private UserLikeAdmin userAdmin;


    @Test
    void contextLoads() {
//        System.out.println(x);
       // System.out.println(bookDao.getAllshops());
//        //System.out.println(bookDao.getshopsById("2"));
      // Shops_detail shops_detail1 = bookDao.getshopsById("4");
//        shops_detail1.setName("777");
//        System.out.println(bookDao.updateshopById(shops_detail1));
//
        //Shops_detail shops_detail2 = bookDao.getshopsById("4");

       // shops_detail2.setContent("ttttt");
       // System.out.println(bookDao.insertShop(shops_detail2));


        System.out.println(u.getAllUserLikeShops());
        System.out.println(u.getUserLikeShopsById("111"));
        System.out.println(u.mostLikeshop());


        System.out.println(userlogin.Login("111","111"));
        System.out.println(userlogin.Login("111","1112"));

        System.out.println(userAdmin.getUserLikeShopsById("111"));

    }

}
