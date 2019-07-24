package com.baizhi.service;

import com.baizhi.dto.UserPageDto;
import com.baizhi.entity.Carousel;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserPageDto queryByPage(Integer page, Integer rows);
    //添加
    String insert(User user);

    //删除
    void delete(String id);

    String update(User user);
    void modifyprofile(User user);

    //查询所有
    List<User> queryAll();

    //根据手机号登陆
    Map<String,Object> login(String phone,String password,String code);

    //注册
    Map<String,Object> regist(User user);
    //根据手机号查一个
    User queryByPhone(String phone);

    //获取手机验证码
    String getCode(String phone);

    Map<String,Object> selectByMouth();

}
