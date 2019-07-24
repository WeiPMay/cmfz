package com.baizhi;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CfmzWpxApplicationTests {
    @Autowired
    private AdminService adminService;
    @Test
    public void contextLoads() {
        adminService.addAdmin(new Admin(UUID.randomUUID().toString(),"123","123456"));

    }
    @Test
    public void test2(){
        Admin admin = adminService.queryOne("1");
        System.out.println(admin);
    }
    @Test
    public void test1(){
        List<Admin> admins = adminService.queryAll();
        for (Admin admin : admins) {
            System.out.println(admin);

        }
    }

}
