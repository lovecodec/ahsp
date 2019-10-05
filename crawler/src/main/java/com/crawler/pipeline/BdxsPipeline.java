package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.po.Expert;

import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class BdxsPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        Expert expert = null;
        if (!resultItems.getAll().entrySet().isEmpty()) {
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                expert = (Expert) entry.getValue();
            }
        }
        if (expert != null) {
            addExpert(expert);
        }
    }

    //添加专家
    public void addExpert(Expert expert) {
        try (Jedis jedis = RedisUtils.getConnection()) {
            jedis.sadd("expertCache1", JSON.toJSONString(expert));
        }
    }
}