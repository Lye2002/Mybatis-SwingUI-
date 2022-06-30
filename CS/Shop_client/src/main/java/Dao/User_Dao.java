package Dao;

import Bean.UserLikeShops;
import Bean.UserLogin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface User_Dao {
    @Select("select username,password from userlogin where username=#{username} and password=#{password}")
    public UserLogin userLogin(@Param("username") String username,@Param("password") String password);

    @Select("select money from userlogin where username=#{username}")
    public Float userMoney(@Param("username")String useranme);

    @Update("update userlogin set money=#{money} where username=#{username}")
    public Boolean updateMoney(@Param("money") Float money,@Param("username")String username);

    @Update("update userlogin set password=#{password} where username=#{username}")
    public Boolean changePassword(@Param("username") String username,@Param("password") String password);

    @Insert("insert into userlogin(username,password,firsttime) values(#{username},#{password},#{firsttime})")
    public Integer addUser(@Param("username") String username,@Param("password") String password,@Param("firsttime")String firsttime);




}
