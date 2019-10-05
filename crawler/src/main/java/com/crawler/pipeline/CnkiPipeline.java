package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.po.Article;
import com.crawler.tools.RedisUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * 存储知网论文数据到redis中
 */
public class CnkiPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Article> articleList = null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            articleList = (List<Article>) entry.getValue();
        }
        if (articleList != null) {
            addArticle(articleList);
        }
    }

    private void addArticle(List<Article> articleList) {
        try (Jedis jedis = RedisUtils.getConnection()) {
            //遍历articleList，获取aritlce对象，转换为json数据，存入数据库
            for (Article article : articleList) {
                jedis.sadd("articleCache1", JSON.toJSONString(article));
            }
        }
    }
}
