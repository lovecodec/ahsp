package com.crawler.pipeline;

import com.crawler.po.Expert;
import com.crawler.tools.JdbcUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

public class RcdtPipeline implements Pipeline {

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<Expert> expertList = null;
		if (!resultItems.getAll().entrySet().isEmpty()) {
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				expertList = (List<Expert>) entry.getValue();
			}
		}
		addExpert(expertList);
	}

	//只添加专家信息
	private void addExpert(List<Expert> expertList) {
		String sql = "REPLACE INTO expert VALUES (?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JdbcUtils.getMysqlConn();
			ps = conn.prepareStatement(sql);
			for (Expert expert : expertList) {
				ps.setString(1, expert.getEid());
				ps.setString(2, expert.getName());
				ps.setString(3, expert.getWorkplace());
				ps.setString(4, expert.getDomain());
				ps.setString(5, expert.getGather());
				ps.addBatch();
			}
			ps.executeBatch();
			JdbcUtils.closeCon(ps, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
