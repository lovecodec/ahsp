package com.ahsp.mapper;

import com.ahsp.po.Admin;
import com.ahsp.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    List<User> loadUserInfo(@Param("upBound")int upBound,@Param("lowerBound") int lowerBound) throws Exception;

    Admin findAdminByUp(@Param("username") String username, @Param("password") String password) throws Exception;

    int loadUserCount() throws Exception;

    int loadAdminCount() throws Exception;

    List<Admin> loadAdminInfo(@Param("upBound")int upBound,@Param("lowerBound") int lowerBound) throws Exception;

    void update_ip_time(@Param("ip")String ip,@Param("time") String time,@Param("username") String username) throws Exception;

}
