package com.baizhi.service;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.UserDao;
import com.baizhi.dto.UserPageDto;
import com.baizhi.entity.Carousel;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import com.baizhi.util.MD5Utils;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Request;
import sun.security.provider.MD5;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpSession httpSession;

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }
    @Override
    public String insert(User user) {
        user.setId(UUID.randomUUID().toString());
        String salt = MD5Utils.getSalt();
        String password = MD5Utils.getPassword(user.getPassword() + salt);
        user.setSalt(salt);
        user.setStatus("正常");
        user.setPassword(password);
        userDao.insert(user);
        return user.getId();
    }

    @Override
    public UserPageDto queryByPage(Integer page, Integer rows) {
        UserPageDto userPageDto = new UserPageDto();
        userPageDto.setPage(page);
        Integer totalCount=userDao.selectTotalCount();
        userPageDto.setTotal(totalCount%rows==0 ? totalCount/rows:totalCount/rows+1);
        userPageDto.setRecords(totalCount);
        userPageDto.setRows(userDao.selectByPage(page,rows));
        return userPageDto;
    }

    @Override
    public String update(User user) {
        User user1 = userDao.selectOne(user.getId());
        if(user1.getStatus().equals("正常")){
            user.setStatus("冻结");
        }else{
            user.setStatus("正常");
        }
        userDao.update(user);
        return user.getId();
    }

    @Override
    public void modifyprofile(User user) {
        userDao.updateprofile(user);
    }

    @Override
    public List<User> queryAll() {
        List<User> users = userDao.queryAll();
        return users;
    }

    @Override
    public Map<String, Object> login(String phone,String password,String code) {
        Map<String, Object> map = new HashMap<>();

        User user = userDao.selectByPhone(phone);
        if(user==null){
            map.put("error","200");
            map.put("message","用户名不存在");
            return map;
        }
        Object code1 = httpSession.getAttribute("code");
        //判断
        if(password==null&&code!=null) {
            if (code1.equals(code)) {
                User user1 = userDao.selectByPhone(user.getPhone());
                map.put("user", user1);
                return map;
            } else {
                map.put("error", "100");
                map.put("message", "验证码错误");
                return map;
            }
        }else if(password!=null){
            User user1 = userDao.selectByPhone(user.getPhone());
            String salt = user1.getSalt();
            String password1 = MD5Utils.getPassword(password+salt);
            if(user1.getPassword().equals(password1)){
                map.put("user",user1);
                return map;
            }else{
                map.put("error","300");
                map.put("message","密码错误");
                return map;
            }
        }else{
            map.put("error","500");
            map.put("message","请输入相应信息");
            return map;
        }
    }

    @Override
    public Map<String,Object> regist(User user) {
        HashMap<String, Object> map = new HashMap<>();
        User user1 = userDao.selectByPhone(user.getPhone());
            user.setId(UUID.randomUUID().toString());
            String salt = MD5Utils.getSalt();
            String password = MD5Utils.getPassword(user.getPassword() + salt);
            user.setSalt(salt);
            user.setStatus("正常");
            user.setPassword(password);
            user.setRegistTime(new Date());
            userDao.regist(user);
            map.put("user",user);


        HashMap<String, Object> map2 = new HashMap<>();
        List<Month> list= userDao.selectByMouth();//月
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<Integer> list4 = new ArrayList<>();
        for (Month month : list) {
            list3.add(month.getMonth());
            list4.add(month.getCount());
        }
        map2.put("month",list3);
        map2.put("month2",list4);

        //男性注册人数
        List<User> users=userDao.queryBySex("男");

        ArrayList<Object> arr = new ArrayList<>();
        for (User user2 : users) {
            HashMap<String, Object> map3 = new HashMap<>();
            map3.put("name",user2.getProvince());
            map3.put("value",user2.getCount());
            arr.add(map3);
        }
        map2.put("man",arr);

        List<User> women = userDao.queryBySex("女");
        ArrayList<Object> brr = new ArrayList<>();
        for (User user3 : women) {
            HashMap<String, Object> map4 = new HashMap<>();
            map4.put("name",user3.getProvince());
            map4.put("value",user3.getCount());
            brr.add(map4);
        }

        map2.put("women",brr);
        Gson gson=new Gson();
        String s1 = gson.toJson(map2);

        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-14b0dee32f8f49cbaf18ab411780d1c5");
        goEasy.publish("demo_channel", s1);


            return map;

    }

    @Override
    public User queryByPhone(String phone) {
        User user = userDao.selectByPhone(phone);
        return user;
    }

    @Override
    public String getCode(String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIlzYGSPoAnKiO", "fbiZsShEHXFxWc0I9PCELWRpoZRILH");
        IAcsClient client = new DefaultAcsClient(profile);

        //随机获取一个6位数
        String str = "123456789";
        StringBuffer code1 = new StringBuffer();
        for(int i=0;i<6;i++){
            code1.append(str.charAt(new Random().nextInt(9)));
        }

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "魏may");
        request.putQueryParameter("TemplateCode", "SMS_171111511");
        request.putQueryParameter("TemplateParam", "{\"code\":"+code1.toString()+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (
                ClientException e) {
            e.printStackTrace();
        }
        httpSession.setAttribute("code",code1.toString());
        return code1.toString();
    }

    @Override
    public  Map<String,Object> selectByMouth() {
        HashMap<String, Object> map = new HashMap<>();
        List<Month> list = userDao.selectByMouth();
        map.put("month",list);
        //男性注册人数
        List<User> users=userDao.queryBySex("男");
        map.put("man",users);
        //女性注册人数
        List<User> list2=userDao.queryBySex("女");
        map.put("women",list2);
        return map;
    }

}
