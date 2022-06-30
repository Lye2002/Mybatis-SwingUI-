package com.example.shop.Service;

import com.example.shop.Bean.Menu;
import com.example.shop.Dao.Show_Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Show_Service implements Show_Dao{
    @Autowired
    public Show_Dao show_dao;


    @Override
    public List<Menu> getMenu() {
        return show_dao.getMenu();
    }
}
