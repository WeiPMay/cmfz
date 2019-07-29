package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //登录
    @RequestMapping("login")
    public String login(Admin admin){
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(),admin.getPassword());
        try {
            subject.login(token);
            return "jsp/main";
        } catch (UnknownAccountException e) {
            System.out.println("当前用户名不存在");
            return "jsp/login";
        }catch (IncorrectCredentialsException i){
            System.out.println("密码错误");
            return "jsp/login";
        }

    }

    //登出
    @RequestMapping("logout")
    public String logout(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "jsp/login";
    }
}
