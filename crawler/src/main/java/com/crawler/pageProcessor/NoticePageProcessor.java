package com.crawler.pageProcessor;

import java.util.*;

import com.crawler.pipeline.NoticePipeline;
import com.crawler.po.Notice;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author Dr.chen
 * @date 2019年05月06日
 * @Description:通知公告爬虫，加入时间判断获取上次爬虫的启动时间，去除在这时间之前的数据。
 */
public class NoticePageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

    @Override
    public void process(Page page) {
        // 第一次访问的时候，完成url添加
        if (page.getUrl().toString().contains("pgi=0")) {
            Selectable count = page.getHtml().regex("\"count\":\".*?\"").regex("\\d+");
            int totalCount = Integer.parseInt(count.get());
            int depth = totalCount / 15 + 1;
            //如果采集的是申请公告
            if (page.getUrl().toString().contains("nid=103")) {
                for (int i = 1; i < depth; i++) {
                    page.addTargetRequest(
                            "http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=103&pgs=15&pgi="
                                    + i);
                }
            } else if (page.getUrl().toString().contains("nid=104")) {
                for (int i = 1; i < depth; i++) {
                    page.addTargetRequest(
                            "http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=104&pgs=15&pgi="
                                    + i);
                }
            }
        }
        // 解析数据，生成notice对象
        Selectable date = page.getHtml().regex("\\d{4}-\\d{2}-\\d{2}");
        Selectable idpk = page.getHtml().regex("\"idpk\":\".*?\"").regex("\\d+");
        Selectable title = page.getHtml().regex("\"title\":\".*?\"").regex("\\d{4}年.*?[）|)]");

        List<String> dateList = date.all();
        List<String> idpkList = idpk.all();
        List<String> titleList = title.all();
        List<Notice> noticeList = new ArrayList<Notice>();

        if (page.getUrl().toString().contains("nid=103")) {
            for (int i = 0; i < dateList.size(); i++) {
                Notice notice = new Notice();
                notice.setNotice_id(UUID.randomUUID().toString());
                notice.setNotice_title(titleList.get(i));
                notice.setNotice_date(dateList.get(i));
                notice.setNotice_href("http://www.nybkjfzzx.cn/Detail.aspx?T=AT&I=" + idpkList.get(i) + "&N=104");
                notice.setNotice_type("申请公告");
                noticeList.add(notice);
            }
        } else if (page.getUrl().toString().contains("nid=104")) {
            for (int i = 0; i < dateList.size(); i++) {
                Notice notice = new Notice();
                notice.setNotice_id(UUID.randomUUID().toString());
                notice.setNotice_title(titleList.get(i));
                notice.setNotice_date(dateList.get(i));
                notice.setNotice_href("http://www.nybkjfzzx.cn/Detail.aspx?T=AT&I=" + idpkList.get(i) + "&N=103");
                notice.setNotice_type("授权公告");
                noticeList.add(notice);
            }
        }
        page.putField("noticeList", noticeList);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        /*
         * 选择爬虫类型, 1.代表品种权申请公告 2.品种权授权公告 3.品种权事务公告
         */
        System.out.println("请输入操作类型:\n1:采集品种权申请公告\n2:采集品种权授权公告");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();

        if (option == 1) {
            // 品种权申请公告
            Spider.create(new NoticePageProcessor()).addUrl(
                    "http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=103&pgs=15&pgi=0")
                    .addPipeline(new NoticePipeline()).thread(3).run();
        } else {
            // 品种权授权公告
            Spider.create(new NoticePageProcessor()).addUrl(
                    "http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=104&pgs=15&pgi=0")
                    .addPipeline(new NoticePipeline()).thread(3).start();
        }
        sc.close();
    }
}
