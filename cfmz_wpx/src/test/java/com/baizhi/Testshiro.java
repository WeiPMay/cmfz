package com.baizhi;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class Testshiro {
    @Test
    public void test1(){
        //获取安全管理器
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory();
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        //指明使用的是哪个安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        //获取令牌token
        UsernamePasswordToken token = new UsernamePasswordToken("wei", "123456");
        try {
            subject.login(token);

        }catch (Exception e){
            e.printStackTrace();
        }
        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);
        boolean vip = subject.hasRole("vip");
        if(vip){
            System.out.println("可以有VIP的所有权限");
        }else{
            System.out.println("不是vip");
        }
        boolean permitted = subject.isPermitted("carouse:add");
        if(permitted){
            System.out.println("当前用户有对轮播图的增加功能");
        }else{
            System.out.println("当前用户不能添加轮播图");
        }


    }
}
