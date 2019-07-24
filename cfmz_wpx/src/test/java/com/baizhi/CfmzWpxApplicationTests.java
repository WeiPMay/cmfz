package com.baizhi;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CfmzWpxApplicationTests {
    @Autowired
    private UserDao userDao;
    @Test
    public void contextLoads() {
        String s = UUID.randomUUID().toString();
        String substring = s.substring(s.length() - 4, s.length());
        DefaultProfile profile=DefaultProfile.getProfile("cn-hangzhou", "LTAIlzYGSPoAnKiO", "fbiZsShEHXFxWc0I9PCELWRpoZRILH");
        IAcsClient client=new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18831434768");
        request.putQueryParameter("SignName", "魏may");
        request.putQueryParameter("TemplateCode", "SMS_171111511");
        request.putQueryParameter("TemplateParam", "{code:8888}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
