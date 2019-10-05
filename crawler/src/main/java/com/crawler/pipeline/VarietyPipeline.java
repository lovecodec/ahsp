package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.po.Variety;
import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

public class VarietyPipeline implements Pipeline {

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<Variety> vList = null;
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			vList = (List<Variety>) entry.getValue();
		}

		if(!vList.isEmpty()){
			addVariety(vList);
		}
	}

	private void addVariety(List<Variety> vList) {
		try (Jedis jedis = RedisUtils.getConnection()) {
			for(Variety variety : vList){
				jedis.sadd("varietyCache1", JSON.toJSONString(variety));
			}
		}
	}

}
