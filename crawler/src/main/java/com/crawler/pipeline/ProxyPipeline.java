package com.crawler.pipeline;

import com.alibaba.fastjson.JSON;
import com.crawler.proxy.MyProxy;
import org.apache.http.HttpHost;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author：Dr.chen
 * @date：2019/8/22 14:08
 * @Description：存放代理数据到redis中,proxyCache
 */
public class ProxyPipeline implements Pipeline{

    @Override
    public void process(ResultItems resultItems, Task task) {

        List<MyProxy> list = (List<MyProxy>)resultItems.getAll().get("hostList");

        try (Jedis jedis = new Jedis("192.168.245.138", 6379)) {
            for(MyProxy host : list){
                String hostString = JSON.toJSONString(host);
                jedis.sadd("proxy_no_check",hostString);
            }
        }
    }
}
