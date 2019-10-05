package com.ahsp.service;

import com.ahsp.mapper.AdminMapper;
import com.ahsp.po.Admin;
import com.ahsp.po.Message;
import com.ahsp.po.PageBean;
import com.ahsp.po.User;
import com.ahsp.utils.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Message adminLogin(String username, String password,String ip) throws Exception {
        Message message = null;
        Admin adminByFind = adminMapper.findAdminByUp(username, password);
        if (adminByFind == null || adminByFind.getAdmin_username().equals("")) {
            message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"登录消息","用户名或密码错误");
        } else {
            //如果登录成功，那么返回消息并且更新用户的ip以及登录时间
            message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"登录消息","登录成功！");
            //更新用户ip和时间
            adminMapper.update_ip_time(ip,CommonUtils.getCurrentTime(),username);
        }
        return message;
    }

    @Override
    public PageBean<User> loadUserInfo(int pc, int ps) throws Exception {
        PageBean<User> pb = new PageBean<>();
        //service层完成pageBean的封装
        int upBound = (pc - 1) * ps;
        int lowerBound = ps;
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(adminMapper.loadUserCount());
        pb.setBeanList(adminMapper.loadUserInfo(upBound, lowerBound));
        return pb;
    }

    @Override
    public PageBean<Admin> loadAdminInfo(int pc, int ps) throws Exception {
        PageBean<Admin> pb = new PageBean<Admin>();
        int upBound = (pc - 1) * ps;
        int lowerBound = ps;
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(adminMapper.loadAdminCount());
        pb.setBeanList(adminMapper.loadAdminInfo(upBound, lowerBound));
        return pb;
    }
}
