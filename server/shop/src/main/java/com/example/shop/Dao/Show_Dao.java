package com.example.shop.Dao;


import com.example.shop.Bean.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Show_Dao {
    @Select("select * from menu")
    public List<Menu> getMenu();

}
