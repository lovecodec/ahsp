package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.po.Notice;
import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

public class NoticePipeline implements Pipeline {

    @SuppressWarnings("unchecked")
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Notice> noticeList = null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            noticeList = (List<Notice>) entry.getValue();
        }

        if (!noticeList.isEmpty()) {
            addNotice(noticeList);
        }
    }

    private void addNotice(List<Notice> noticeList) {
        try (Jedis jedis = RedisUtils.getConnection()) {
            for (Notice notice : noticeList) {
                jedis.sadd("noticeCache1", JSON.toJSONString(notice));
            }
        }
    }
}
