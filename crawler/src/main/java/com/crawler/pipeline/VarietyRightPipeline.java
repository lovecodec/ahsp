package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.po.VarietyRight;
import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

public class VarietyRightPipeline implements Pipeline{

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<VarietyRight> vrList = null;
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			vrList = (List<VarietyRight>) entry.getValue();
		}

		if(!vrList.isEmpty()){
			addVr(vrList);
		}
	}
	
	private void addVr(List<VarietyRight> vrList) {
		try (Jedis jedis = RedisUtils.getConnection()) {
			for(VarietyRight vr : vrList){
				jedis.sadd("vrCache1", JSON.toJSONString(vr));
			}
		}
	}
}