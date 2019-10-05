package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.po.AppNoticeDetails;
import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class NoticeDetailPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        AppNoticeDetails appNoticeDetails = resultItems.get("apd");
        addNoticeDetails(appNoticeDetails);
    }

    public void addNoticeDetails(AppNoticeDetails apd) {
        try (Jedis jedis = RedisUtils.getConnection()) {
            jedis.sadd("noticeDetailCache1", JSON.toJSONString(apd));
        }
    }
}
