package com.crawler.common;

import com.crawler.tools.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;



public class NoticeDetailDb {
	/**
	 * 查找所有申请了的数据
	 * @return
	 */
	public static List<String> getAppVrName(){
		List<String> list = new LinkedList<String>();
		Connection conn ;
		PreparedStatement ps;
		ResultSet rs;
		String sql = "SELECT vrName FROM variety_right";
		try {
			conn = JdbcUtils.getMysqlConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				String vrName = rs.getString("vrName");
				list.add(vrName);
			}
			JdbcUtils.closeCon(rs, ps, conn);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 查找授权号不为空的数据的vrname
	 * @return
	 */
	public static List<String> getGrantVrName(){
		List<String> list = new LinkedList<String>();
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		String sql = "SELECT vrName FROM variety_right where grantCode!=?";
		try {
			conn = JdbcUtils.getMysqlConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "");
			rs = ps.executeQuery();
			while(rs.next()) {
				String vrName = rs.getString("vrName");
				list.add(vrName);
			}
			JdbcUtils.closeCon(rs, ps, conn);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
