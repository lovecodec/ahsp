package com.ahsp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author：Dr.chen
 * @date：2019/8/20 20:35
 * @Description：
 */
@Repository
public class RedisDao {
    public static final String EXPERT_CACHE_1 = "expertCache1";
    public static final String ARTICLE_CACHE_1 = "articleCache1";
    public static final String NEWS_CACHE_1 = "newsCache1";
    public static final String NOTICE_CACHE_1 = "noticeCache1";
    public static final String NOTICE_DETAIL_CACHE_1 = "noticeDetailCache1";
    public static final String PATENT_CACHE_1 = "patentCache1";
    public static final String VR_CACHE_1 = "vrCache1";
    public static final String VARIETY_CACHE_1 = "varietyCache1";
    public static final String PROXY_CHECKED_CACHE = "proxy_check";
    public static final String PROXY_NO_CHECKED_CACHE = "proxy_no_check";

    public static final String EXPERT_CACHE_2 = "expertCache2";
    public static final String ARTICLE_CACHE_2 = "articleCache2";
    public static final String NEWS_CACHE_2 = "newsCache2";
    public static final String NOTICE_CACHE_2 = "noticeCache2";
    public static final String NOTICE_DETAIL_CACHE_2 = "noticeDetailCache2";
    public static final String PATENT_CACHE_2 = "patentCache2";
    public static final String VR_CACHE_2 = "vrCache2";
    public static final String VARIETY_CACHE_2 = "varietyCache2";

    //注入Redis连接池
    @Autowired
    private JedisPool jedisPool;

    //获取上次爬取的专家数据
    public Set<String> getResultByCacheName(String cacheName) throws Exception {
        Set<String> set;
        try (Jedis jedis = jedisPool.getResource()) {
            set = jedis.smembers(cacheName);
        }
        return set;
    }

    //将两个集合进行并集操作，并且保存在destination集合，用于将未爬取的数据变更到cache2
    public void unionCache(String cache1, String cache2, String destinationCache) throws Exception {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sunionstore(destinationCache, cache1, cache2);
        }
    }

    //删除指定集合
    public void removeCache(String cacheName) throws Exception {
        try (Jedis jedis = jedisPool.getResource()){
            jedis.del(cacheName);
        }
    }

//    //获取上次爬取的论文数据
//    public Set<String> getArticleByCrawler() throws Exception{
//        return this.getResult(articleCache);
//    }
//
//    //获取上次爬取的新闻数据
//    public Set<String> getNewsByCrawler() throws Exception{
//        return this.getResult(newsCache);
//    }
//
//    //获取上次爬取的通知公告
//    public Set<String> getNoticeByCrawler() throws Exception{
//        return this.getResult(noticeCache);
//    }
//
//    //获取上次爬取的公告详情
//    public Set<String> getNoticeDetailByCrawler() throws Exception{
//        return this.getResult(noticeDetailCache);
//    }
//
//    //获取上次爬取的专利
//    public Set<String> getPatentByCrawler() throws Exception{
//        return this.getResult(patentCache);
//    }
//
//    //获取上次爬取的品种权
//    public Set<String> getVrByCrawler() throws Exception{
//        return this.getResult(vrCache);
//    }
//
//    //获取上次爬取的审定品种
//    public Set<String> getVarietyByCrawler() throws Exception{
//        return this.getResult(varietyCache);
//    }

    public int getResultCount(String cacheName) {
        int totalCount;
        Long l;
        try (Jedis jedis = jedisPool.getResource()) {
            l = jedis.scard(cacheName);
            totalCount = l.intValue();
        }
        return totalCount;
    }
}

