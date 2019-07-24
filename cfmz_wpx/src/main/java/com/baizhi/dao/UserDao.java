package com.baizhi.dao;

import com.baizhi.entity.Carousel;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //分页
    List<User> selectByPage(@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);
    //查询总行数
    Integer selectTotalCount();

    //添加
    void insert(User user);
    //删除
    void delete(String id);
    //修改
    void update(User user);

    User selectOne(String id);

    void updateprofile(User user);

    //查询所有
    List<User> queryAll();


    //根据手机号查一个
    User selectByPhone(String phone);

    //注册
    void regist(User user);
    //查询每月注册量
    List<Month> selectByMouth();

    List<User> queryBySex(String gender);


}
