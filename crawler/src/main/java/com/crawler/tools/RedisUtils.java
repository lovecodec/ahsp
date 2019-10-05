package com.crawler.tools;

import redis.clients.jedis.Jedis;

/**
 * @author：Dr.chen
 * @date：2019/8/20 17:51
 * @Description：
 */
public class RedisUtils {

    public static Jedis getConnection(){
        return new Jedis("192.168.245.138",6379);
    }
}
