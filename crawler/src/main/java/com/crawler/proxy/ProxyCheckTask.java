package com.crawler.proxy;

import com.alibaba.fastjson.JSON;
import com.crawler.tools.SpiderTools;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import redis.clients.jedis.Jedis;

/**
 * @author：Dr.chen
 * @date：2019/8/22 22:13
 * @Description：线程操作,读取资源并且校验,如果可用，计算出时延
 */
public class ProxyCheckTask implements Runnable {

    private ProxySource proxySource;

    public ProxyCheckTask(ProxySource proxySource) {
        this.proxySource = proxySource;
    }

    @Override
    public void run() {
        while (!proxySource.isEmpty()) {
            long begin = System.currentTimeMillis();
            long end = 0L;
            MyProxy myProxy = proxySource.getProxy();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            HttpGet httpGet = new HttpGet("https://www.baidu.com");
            RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(myProxy.getHostName(), myProxy.getPort()))
                    .setSocketTimeout(10000).setConnectionRequestTimeout(10000).setConnectTimeout(10000).build();
            httpGet.setConfig(config);
            try {
                response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    end = System.currentTimeMillis();
                    myProxy.setDelay(end - begin);
                    myProxy.setCheckTime(SpiderTools.getCurrentTime());
                    System.out.println("此代理可用" + myProxy + "，已添加到数据库");
                    //将代理存入数据库
                    try (Jedis jedis = new Jedis("192.168.245.138", 6379)) {
                        jedis.sadd("proxy_check", JSON.toJSONString(myProxy));
                    }
                }
            } catch (Exception e) {
                //如果超时表示代理不可用，跳过
            } finally {
                try {
                    httpClient.close();
                    if (response != null) {
                        response.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
