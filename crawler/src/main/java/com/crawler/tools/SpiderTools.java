package com.crawler.tools;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderTools {

    //获取unix时间戳
    public static int getTimeStamp() {
        int time = (int) (System.currentTimeMillis() / 1000);
        return time;
    }


    //将unix时间戳转换为普通时间
    public static String getNormalTime(String timestamp) {
        Long newTimestamp = Long.parseLong(timestamp);
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date(newTimestamp));
    }

    public static String getCurrentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateStr = sdp.format(date);
        return dateStr;
    }

    //提取出文本中的数字利用正则表达式
    public static String getNumberFromText(String text) {
        String result = "";
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(text);
        while (m.find()) {
            result = m.group();
        }
        return result;
    }

    //关闭连接
    public static void closeAllConnection(HttpRequestBase request, CloseableHttpClient httpClient, CloseableHttpResponse response) throws Exception{
        request.releaseConnection();
        httpClient.close();
        response.close();
    }

    public static String randomUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
