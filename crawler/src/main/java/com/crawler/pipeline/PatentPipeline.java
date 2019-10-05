package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.po.Patent;
import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

public class PatentPipeline implements Pipeline {

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<Patent> patentList = null;
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			patentList = (List<Patent>) entry.getValue();
		}

		if(!patentList.isEmpty()){
			addPatent(patentList);
		}
	}

	private void addPatent(List<Patent> patentList) {
		try (Jedis jedis = RedisUtils.getConnection()) {
			for(Patent patent : patentList) {
				jedis.sadd("patentCache1", JSON.toJSONString(patent));
			}
		}
	}
}
