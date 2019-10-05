package com.crawler.pageProcessor;

import com.crawler.po.Article;
import com.crawler.tools.SpiderTools;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


public class CnkiPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

    @Override
    public void process(Page page) {
        if (page.getUrl().get().equals("http://kns.cnki.net/kns/request/SearchHandler.ashx")) {
            //添加结果页面连接
            String payload = page.getRawText();
            String resultPageUrl = "http://kns.cnki.net/kns/brief/brief.aspx?pagename=" + payload + "&recordsperpage=50";
            page.addTargetRequest(resultPageUrl);
        }
        //进入结果界面，解析数据
        else{
            /*每页结果数*/
            int resultCount = Integer.parseInt(page.getHtml().regex("找到.*?条结果").regex("\\d+").get());
            /**解析数据*/
            Document dom = page.getHtml().getDocument();
            List<Article> articleList = getArticleList(dom, resultCount);
            page.putField("articleList",articleList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 解析论文数据
     */
    private List<Article> getArticleList(Document dom, int resultCount) {
        List<Article> articleList = new ArrayList<>();
        for (int i = 2; i < 2 + resultCount; i++) {
            Article article = new Article();
            String titleCss = ".GridTableContent > tbody:nth-child(1) > tr:nth-child(" + i + ") > td:nth-child(2)";
            String authorCss = ".GridTableContent > tbody:nth-child(1) > tr:nth-child(" + i + ") > td:nth-child(3)";
            String pubPlaceCss = ".GridTableContent > tbody:nth-child(1) > tr:nth-child(" + i + ") > td:nth-child(4)";
            String yearCss = ".GridTableContent > tbody:nth-child(1) > tr:nth-child(" + i + ") > td:nth-child(5)";
            String citationCss = ".GridTableContent > tbody:nth-child(1) > tr:nth-child(" + i + ") > td:nth-child(7)";
            //end of css selector
            String author = dom.select(authorCss).text();
            String year = dom.select(yearCss).text();
            String pubPlace = "《" + dom.select(pubPlaceCss).text() + "》";
            article.setAid(SpiderTools.randomUUID());
            article.setTitle(dom.select(titleCss).text());
            article.setPubPlace(pubPlace);
            article.setYear(year);
            article.setCitation(dom.select(citationCss).text());
            article.setAuthor(author);
            article.setUrl("http://kns.cnki.net" + dom.select(titleCss).select("a").attr("href").replace("kns","KCMS"));
            article.setInfo(year + "-" + author + "-" + pubPlace);
            articleList.add(article);
        }
        return articleList;
    }

    public static void main(String[] args) throws Exception {
        //创建Spidera
        //Spider cnkiSpider = Spider.create(new CnkiPageProcessor());
        //组装表单
//        Map<String, Object> params = new LinkedHashMap<String, Object>();
//        params.put("action", "");
//        params.put("ConfigFile", "SCDB.xml");
//        params.put("db_opt", "CJFQ,CDFD,CMFD,CPFD,IPFD,CCND,CCJD");
//        params.put("DBCatalog", "中国学术文献网络出版总库");
//        params.put("DbPrefix", "SCDB");
//        params.put("his", "0");
//        params.put("isinEn", "1");
//        params.put("NaviCode", "*");
//        params.put("PageName", "ASP.brief_result_aspx");
//        params.put("au_1_sel", "FI");
//        params.put("au_1_sel2", "AF");
//        params.put("au_1_special1", "=");
//        params.put("au_1_special2", "%");
//        params.put("au_1_value1", "吴爽");
//        params.put("au_1_value2", "安徽省农业科学院");
//        params.put("ua", "1.21");
//        //生成请求
//        Request request = new Request("http://kns.cnki.net/kns/request/SearchHandler.ashx");
//        request.setMethod(HttpConstant.Method.POST);
//        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
//        cnkiSpider.addRequest(request).addPipeline(new CnkiPipeline()).thread(1).run();


//        //组装表单
//        Map<String, Object> params = new LinkedHashMap<String, Object>();
//        params.put("action", "");
//        params.put("ConfigFile", "SCDB.xml");
//        params.put("db_opt", "CJFQ,CDFD,CMFD,CPFD,IPFD,CCND,CCJD");
//        params.put("DBCatalog", "中国学术文献网络出版总库");
//        params.put("DbPrefix", "SCDB");
//        params.put("his", "0");
//        params.put("isinEn", "1");
//        params.put("NaviCode", "*");
//        params.put("PageName", "ASP.brief_result_aspx");
//        params.put("au_1_sel", "FI");
//        params.put("au_1_sel2", "AF");
//        params.put("au_1_special1", "=");
//        params.put("au_1_special2", "%");   //工作地点参数是
//        params.put("au_1_value1", "吴爽"); //这个参数是专家名称
//        params.put("au_1_value2", "安徽省农业科学院");      //这个参数是工作地点
//        params.put("ua", "1.21");
//        //生成请求
//        Request request = new Request("http://kns.cnki.net/kns/request/SearchHandler.ashx");
//        request.setMethod(HttpConstant.Method.POST);
//        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
//        cnkiSpider.addRequest(request).addPipeline(new ConsolePipeline()).thread(1).run();
    }
}
