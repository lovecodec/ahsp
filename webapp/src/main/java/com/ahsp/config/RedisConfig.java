package com.ahsp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author：Dr.chen
 * @date：2019/8/29 15:31
 * @Description：
 */
@Configuration
public class RedisConfig {
    // 连接信息
    private static final String HOST = "192.168.245.138";
    private static final int PORT = 6379;

    //配置redis连接池
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 基本配置
        poolConfig.setMaxTotal(1000);           // 最大连接数
        poolConfig.setMaxIdle(32);              // 最大空闲连接数
        poolConfig.setMaxWaitMillis(100 * 1000);  // 最大等待时间
        poolConfig.setTestOnBorrow(true);       // 检查连接可用性, 确保获取的redis实例可用
        return new JedisPool(poolConfig, HOST, PORT);
    }
}
