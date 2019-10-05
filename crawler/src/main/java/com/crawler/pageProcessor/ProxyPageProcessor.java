package com.crawler.pageProcessor;

import com.crawler.pipeline.ProxyPipeline;
import com.crawler.proxy.MyProxy;
import com.crawler.tools.SpiderTools;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;


import java.util.ArrayList;
import java.util.List;

/**
 * @author：Dr.chen
 * @date：2019/8/22 14:07
 * @Description：获取代理，ip + port
 */
public class ProxyPageProcessor implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000).setTimeOut(10000).addHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

    @Override
    public void process(Page page) {
        if(page.getStatusCode() == 503){
            System.out.println("ip被封");
        }
        Document dom = page.getHtml().getDocument();
        //#ip_list > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2) --ip_firstLine

        //#ip_list > tbody:nth-child(1) > tr:nth-child(101) > td:nth-child(2)   --ip_lastLine
        //#ip_list > tbody:nth-child(1) > tr:nth-child(101) > td:nth-child(3)   --port

        List<MyProxy> hostList = new ArrayList<>();
        for (int i = 2; i <= 101; i++) {
            String ipCss = "#ip_list > tbody:nth-child(1) > tr:nth-child("  + i + ") > td:nth-child(2)";
            String portCss = "#ip_list > tbody:nth-child(1) > tr:nth-child(" + i + ") > td:nth-child(3)";
            String ip = dom.select(ipCss).text();
            int port = Integer.parseInt(dom.select(portCss).text());
            hostList.add(new MyProxy(ip,port,0L, SpiderTools.getCurrentTime()));
        }
        page.putField("hostList",hostList);
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new ProxyPageProcessor()).addPipeline(new ProxyPipeline()).thread(1);
        for (int i = 24; i <= 100 ; i++) {
            spider.addUrl("https://www.xicidaili.com/nn/" + i);
        }
        spider.start();
    }
}
