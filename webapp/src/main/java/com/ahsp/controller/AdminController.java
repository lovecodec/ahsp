package com.ahsp.controller;


import com.ahsp.po.Admin;
import com.ahsp.po.Message;
import com.ahsp.po.PageBean;
import com.ahsp.po.User;
import com.ahsp.service.AdminService;
import com.ahsp.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class AdminController {

    @Autowired
    @Qualifier("adminService")
    private AdminService adminService;

    @RequestMapping("/adminLogin")
    @ResponseBody
    public Message adminLogin(String username, String password, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
        //获取当前管理员的ip地址
        String ip = request.getRemoteAddr();
        Message message = adminService.adminLogin(username, password, ip);
        if (message.getMessage_content().equals("登录成功！")) {
            Cookie cookie = new Cookie("admin", username);
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return message;
    }

    @RequestMapping("/getSession_admin")
    @ResponseBody
    public Message getSession_admin(HttpSession session) throws Exception {
        String user = "";
        if (session.getAttribute("session_admin") != null) {
            user = (String) session.getAttribute("session_user");
        }
        Message message = null;
        if (!user.equals("") && !user.isEmpty()) {
            CommonUtils.configMessage(CommonUtils.getCurrentTime(),"提示消息","session_user:" + user);
        } else {
            message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"警告消息","您还没有登录，请去登录QAQ!");
        }
        return message;
    }

    @RequestMapping("/loadUserInfo")
    @ResponseBody
    public PageBean<User> loadUserInfo(String pc) throws Exception {
        int currentPage = Integer.parseInt(pc);
        int ps = 10;
        return adminService.loadUserInfo(currentPage, ps);
    }

    @RequestMapping("/loadAdminInfo")
    @ResponseBody
    public PageBean<Admin> loadAdminInfo(String pc) throws Exception {
        int currentPage = Integer.parseInt(pc);
        int ps = 10;
        return adminService.loadAdminInfo(currentPage, ps);
    }

    //注销登录
    @RequestMapping("/adminLogout")
    public String adminLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("admin")){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/back/admin/login.html";
            }
        }
        return "redirect:/back/admin/login.html";
    }
}