package com.crawler.pageProcessor;

import com.crawler.tools.SpiderTools;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dr.chen
 * @date 2018年10月29日
 * @Description:知网爬虫
 */
public class CnkiSpider {

    public static void main(String[] args) throws Exception {
        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        // 创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse res = null;
        //创建一个请求来获取cookie
        HttpGet get = new HttpGet("http://www.cnki.net");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
        //执行请求
        res = httpClient.execute(get, context);
        get.releaseConnection();
        res.close();
        Thread.sleep(2000);
        // 构造一个新的get请求，用来测试登录是否成功
        HttpPost hPost = new HttpPost("http://kns.cnki.net/kns/request/SearchHandler.ashx");
        List<NameValuePair> form = new ArrayList<NameValuePair>();
        form.add(new BasicNameValuePair("action", ""));
        form.add(new BasicNameValuePair("ConfigFile", "SCDB.xml"));
        form.add(new BasicNameValuePair("db_opt", "CJFQ,CDFD,CMFD,CPFD,IPFD,CCND,CCJD"));
        form.add(new BasicNameValuePair("DBCatalog", "中国学术文献网络出版总库"));
        form.add(new BasicNameValuePair("DbPrefix", "SCDB"));
        form.add(new BasicNameValuePair("his", "0"));
        form.add(new BasicNameValuePair("isinEn", "1"));
        form.add(new BasicNameValuePair("NaviCode", "D"));
        form.add(new BasicNameValuePair("PageName", "ASP.brief_result_aspx"));
        form.add(new BasicNameValuePair("au_1_sel", "AU"));
        form.add(new BasicNameValuePair("au_1_sel2", "AF"));
        form.add(new BasicNameValuePair("au_1_special1", "="));
        form.add(new BasicNameValuePair("au_1_special2", "%"));
        form.add(new BasicNameValuePair("au_1_value1", "赵开军"));
        form.add(new BasicNameValuePair("ua", "1.21"));
        hPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
        //hPost设置参数
        hPost.setEntity(entity);
        //执行请求
        res = httpClient.execute(hPost, context);
        //获取响应内容
        String payload = EntityUtils.toString(res.getEntity());
        res.close();
        Thread.sleep(1000);
        //获取查询到的内容
        String url = "http://kns.cnki.net/kns/brief/brief.aspx?pagename=" + payload;
        HttpGet newGet = new HttpGet(url);
        newGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
        res = httpClient.execute(newGet);
        String source = EntityUtils.toString(res.getEntity());
        //Jsoup解析
        Document dom = Jsoup.parse(source);
        Elements trs = dom.getElementsByAttribute("bgcolor");
        for(Element tr : trs){
            System.out.println(tr.text());
        }

        Pattern p = Pattern.compile("\\?curpage=.*?\"");
        Matcher m = p.matcher(source);
        while (m.find()){
            String temp = m.group();
            url = "http://kns.cnki.net/kns/brief/brief.aspx" + temp.replace("\"","");
        }
        newGet = new HttpGet(url);
        res = httpClient.execute(newGet);
        res = httpClient.execute(newGet);
        source = EntityUtils.toString(res.getEntity());

        SpiderTools.closeAllConnection(newGet,httpClient,res);
    }
}
