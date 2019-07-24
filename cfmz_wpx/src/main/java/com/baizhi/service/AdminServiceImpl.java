package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private HttpSession httpSession;
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Admin> queryAll() {
        List<Admin> admins = adminDao.selectAll();
        return admins;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public Map<String, Object> login(Admin admin) {
        Map<String , Object> map = new HashMap<>();
        Admin selectedAdmin = adminDao.login(admin.getUsername());
        httpSession.setAttribute("admin",selectedAdmin);
        if (selectedAdmin == null){
            map.put("code",300);
            map.put("message","用户名不存在");
        }else if (selectedAdmin.getPassword().equals(admin.getPassword())){
            map.put("code",200);
            map.put("message","登陆成功");
        }else{
            map.put("code",400);
            map.put("message","密码错误");
        }
        return map;
    }
}
