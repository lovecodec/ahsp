package com.crawler.proxy;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author：Dr.chen
 * @date：2019/8/22 14:27
 * @Description：代理资源类
 */
public class ProxySource {

    //存放的资源是代理队列，利用队列的特点，先进先出，弹出一个数据队列就少一个数据
    private ConcurrentLinkedQueue<MyProxy> proxyQueue;

    public ProxySource() {
        this.proxyQueue = new ConcurrentLinkedQueue<>();
    }

    //给队列中填充数据，从redis缓存中读取
    public void addProxy() {
        try (Jedis jedis = new Jedis("192.168.245.138",6379)) {
            Set<String> members = jedis.smembers("proxy_no_check");
            Iterator<String> iterator = members.iterator();
            while (iterator.hasNext()){
                MyProxy myProxy = JSON.parseObject(iterator.next(), MyProxy.class);
                proxyQueue.add(myProxy);
            }
        }
    }

    //从队列中取出数据
    public MyProxy getProxy() {
        //判断队列中数据是否为空
        if (proxyQueue.isEmpty())
            return null;
        return proxyQueue.poll();
    }

    //
    public Boolean isEmpty(){
        return proxyQueue.isEmpty();
    }
}
