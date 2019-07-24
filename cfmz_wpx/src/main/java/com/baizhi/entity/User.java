package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baizhi.annotation.UserAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    //编号
    @Excel(name="用户编号")
    private String id;
    //电话
    @Excel(name="电话")
    private String phone;
    //密码
    @Excel(name="密码")
    private String password;
    //盐
    @Excel(name="盐")
    private String salt;
    //法名
    @Excel(name="法名")
    private String dharmaName;
    //省
    @Excel(name="省")
    private String province;
    //市
    @Excel(name="市")
    private String city;
    //性别
    @Excel(name="性别")
    private String gender;
    //签名
    @Excel(name="签名")
    private String personalSign;
    //头像
    @Excel(name="头像", type = 2 ,width = 40, height = 20,imageType = 1)
    private String profile;
    //状态
    @Excel(name="状态")
    private String status;
    //注册时间
    @Excel(name="注册时间",format = "yyyy-MM-dd")
    private Date registTime;

    private String count;

}
