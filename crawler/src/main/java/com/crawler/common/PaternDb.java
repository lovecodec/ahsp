package com.crawler.common;

import com.crawler.po.Expert;
import com.crawler.tools.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class PaternDb {

	/**
	 * 获取所有专家的姓名、工作单位、
	 * @return
	 */
	public static List<Expert> getExpertList(){
		List<Expert> expertList = new ArrayList<Expert>();
		
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		String sql = "SELECT eid,name,workplace FROM expert";
		try {
			conn = JdbcUtils.getMysqlConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Expert e = new Expert();
				e.setName(rs.getString("name"));
				e.setWorkplace(rs.getString("workplace"));
				e.setEid(rs.getString("eid"));
				expertList.add(e);
			}
			JdbcUtils.closeCon(rs, ps, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expertList;
	}
	
}
