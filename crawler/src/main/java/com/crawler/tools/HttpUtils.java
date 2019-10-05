package com.crawler.tools;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HttpUtils {

    public static final String TAG = "HttpUtils";
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpClientContext context = new HttpClientContext();

    private HttpUtils() {

    }

    public static String sendGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientContext context = new HttpClientContext();
        CloseableHttpResponse response = null;
        String content = null;
        try {
            HttpGet get = new HttpGet(url);
            response = httpClient.execute(get, context);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
            // System.out.println(TAG + "GET:" + content);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        return content;
    }

    public static String sendPost(String url, List<NameValuePair> nvps) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientContext context = new HttpClientContext();
        CloseableHttpResponse response = null;
        String content = null;
        try {
            // 　HttpClient中的post请求包装类
            HttpPost post = new HttpPost(url);
            post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");
            // nvps是包装请求参数的list
            if (nvps != null) {
                post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }
            // 执行请求用execute方法，content用来帮我们附带上额外信息
            response = httpClient.execute(post, context);
            // 得到相应实体、包括响应头以及相应内容
            HttpEntity entity = response.getEntity();
            // 得到response的内容
            content = EntityUtils.toString(entity, "utf-8");
            // System.out.println(TAG + "POST:" + content);
            // 　关闭输入流
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null){
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return content;
    }
}
