package com.example.shop.Service;

import com.example.shop.Bean.Shops_detail;
import com.example.shop.Dao.shop_detail;
import com.example.shop.controller.config.R;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Shop_detailService implements shop_detail {
    @Qualifier("shop_detail")
    @Autowired
    private shop_detail shopDao;

    @Override
    public List<Shops_detail> getAllshops() {
        return shopDao.getAllshops();
    }

    @Override
    public List<Shops_detail> getshopsByName(String name) {
        return shopDao.getshopsByName(name);
    }

    @Override
    public List<Shops_detail> getshopsByPriceBetwwen(Integer low,Integer heigh) {
        return shopDao.getshopsByPriceBetwwen(low, heigh);
    }

    @Override
    public Shops_detail getshopsById(String id) {
        return shopDao.getshopsById(id);
    }

    @Override
    public int updateshopById(Shops_detail s) {
        System.out.println(s);
        return shopDao.updateshopById(s);
    }

    @Override
    public int deleteShopById(int id) {
        return shopDao.deleteShopById(id);
    }

    @Override
    public int insertShop(Shops_detail s) {
        return shopDao.insertShop(s);
    }

    @Override
    public List<Shops_detail> fpage(int pageNum, int Size) {
        return shopDao.fpage(pageNum,Size);
    }

    @Override
    public Integer delOrderByShopId(String orderid) {
        return null;
    }
}
