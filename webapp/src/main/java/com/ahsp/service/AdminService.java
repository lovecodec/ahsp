package com.ahsp.service;

import com.ahsp.po.Admin;
import com.ahsp.po.Message;
import com.ahsp.po.PageBean;
import com.ahsp.po.User;

import java.util.List;

public interface AdminService {
    Message adminLogin(String username,String password,String ip) throws Exception;

    PageBean<User> loadUserInfo(int pc,int ps) throws Exception;

    PageBean<Admin> loadAdminInfo(int pc,int ps) throws Exception;
}
