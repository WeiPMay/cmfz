package com.baizhi.dao;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminDao {
    List<Admin> selectAll();

    Admin login(String username);
    void insertAdmin(Admin admin);
}
