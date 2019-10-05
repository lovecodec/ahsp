package com.crawler.pipeline;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.crawler.po.News;
import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Dr.chen
 * @date 2019年04月22日
 * @Description:数据来源，百度百科
 */
public class NewsPipeline implements Pipeline {

    @SuppressWarnings("unchecked")
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<News> newsList = null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            newsList = (List<News>) entry.getValue();
        }
        if (!newsList.isEmpty()) {
            addNews(newsList);
        }
    }

    // 添加操作
    private void addNews(List<News> newsList) {
        try (Jedis jedis = RedisUtils.getConnection()) {
            for (News news : newsList) {
                String newsJson = JSON.toJSONString(news);
                jedis.sadd("newsCache1", newsJson);
            }
        }
    }
}
