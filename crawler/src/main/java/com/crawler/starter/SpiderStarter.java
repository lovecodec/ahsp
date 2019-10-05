package com.crawler.starter;

import com.crawler.common.NoticeDetailDb;
import com.crawler.common.PaternDb;
import com.crawler.pageProcessor.*;
import com.crawler.pipeline.*;
import com.crawler.po.Expert;
import com.crawler.proxy.ProxyCheckTask;
import com.crawler.proxy.ProxySource;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dr.chen
 * @date 2019/7/16
 * @description 爬虫启动器
 */
public class SpiderStarter {
    /**
     * 启动cnki爬虫
     */
    public Spider startCnkiSpider(Spider spider, HashMap<String, String> parameterMap) {
        //获取线程数
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        //获取精确类型
        String accuracy = parameterMap.get("accuracy");
        //创建爬虫
        spider = Spider.create(new CnkiPageProcessor()).addPipeline(new CnkiPipeline()).thread(threadNum);
        //没有参数，更新所有专家的论文信息
        if (accuracy.equals("0")) {
            //组装表单
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("action", "");
            params.put("ConfigFile", "SCDB.xml");
            params.put("db_opt", "CJFQ,CDFD,CMFD,CPFD,IPFD,CCND,CCJD");
            params.put("DBCatalog", "中国学术文献网络出版总库");
            params.put("DbPrefix", "SCDB");
            params.put("his", "0");
            params.put("isinEn", "1");
            params.put("NaviCode", "*");
            params.put("PageName", "ASP.brief_result_aspx");
            params.put("au_1_sel", "FI");       //第一作者
            params.put("au_1_sel2", "AF");
            params.put("au_1_special1", "=");
            params.put("au_1_special2", "%");   //工作地点参数是
            params.put("au_1_value1", "赵开军"); //这个参数是专家名称
            params.put("au_1_value2", "");      //这个参数是工作地点
            params.put("ua", "1.21");
            //生成请求
            Request request = new Request("http://kns.cnki.net/kns/request/SearchHandler.ashx");
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
            spider.addRequest(request);
        } else if (accuracy.equals("1")) {
            //根据专家信息采集
            String expertName = parameterMap.get("expertName");
            String workplace = parameterMap.get("workplace");
            //组装表单
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("action", "");
            params.put("ConfigFile", "SCDB.xml");
            params.put("db_opt", "CJFQ,CDFD,CMFD,CPFD,IPFD,CCND,CCJD");
            params.put("DBCatalog", "中国学术文献网络出版总库");
            params.put("DbPrefix", "SCDB");
            params.put("his", "0");
            params.put("isinEn", "1");
            params.put("NaviCode", "*");
            params.put("PageName", "ASP.brief_result_aspx");
            params.put("au_1_sel", "FI");
            params.put("au_1_sel2", "AF");
            params.put("au_1_special1", "=");
            params.put("au_1_special2", "%");   //工作地点参数是
            params.put("au_1_value1", expertName); //这个参数是专家名称
            params.put("au_1_value2", workplace);      //这个参数是工作地点
            params.put("ua", "1.21");
            //生成请求
            Request request = new Request("http://kns.cnki.net/kns/request/SearchHandler.ashx");
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
            spider.addRequest(request);
        } else {
            //根据论文信息采集
            String articleName = parameterMap.get("articleName");
            String publishdate_from = parameterMap.get("publishdate_from");
            String publishdate_to = parameterMap.get("publishdate_to");
            //组装表单
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("action", "");
            params.put("NaviCode", "D");
            params.put("ua", "1.21");
            params.put("isinEn", "1");
            params.put("PageName", "ASP.brief_result_aspx");
            params.put("DbPrefix", "SCDB");
            params.put("DBCatalog", "中国学术文献网络出版总库");
            params.put("ConfigFile", "SCDB.xml");
            params.put("db_opt", "CJFQ,CDFD,CMFD,CPFD,IPFD,CCND,CCJD");
            params.put("publishdate_from", publishdate_from);
            params.put("publishdate_to", publishdate_to);
            params.put("txt_1_sel", "SU$%=|");
            params.put("txt_1_value1", articleName);
            params.put("txt_1_relation", "#CNKI_AND");
            params.put("txt_1_special1", "=");
            params.put("his", "0");
            Request request = new Request("http://kns.cnki.net/kns/request/SearchHandler.ashx");
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
            spider.addRequest(request);
        }
        return spider;
    }

    /**
     * 启动百度学术爬虫
     */
    public Spider startBdxsSpider(Spider spider, HashMap<String, String> parameterMap) {
        //获取线程数
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        //查看爬虫精确类型
        String accuracy = parameterMap.get("accuracy");
        if (accuracy.equals("0")) {
            String url = "http://xueshu.baidu.com/scholarID/CN-BL74C1PJ";
            spider = Spider.create(new BdxsPageProcessor()).addUrl(url).addPipeline(new BdxsPipeline()).thread(threadNum);
        } else {
            //获取专家姓名和工作地点
            String expertName = parameterMap.get("expertName");
            String workplace = parameterMap.get("workplace");
            String url = "http://xueshu.baidu.com/usercenter/data/authorchannel?cmd=inject_page&author=" + expertName + "&affiliate=" + workplace;
            spider = Spider.create(new BdxsAccuratePageProcessor()).addUrl(url).addPipeline(new BdxsPipeline()).thread(threadNum);
        }
        return spider;
    }

    /**
     * 启动通知公告爬虫
     */
    public Spider startNoticeSpider(Spider spider, HashMap<String, String> parameterMap) {
        //获取线程数
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        //检查类型
        String noticeType = parameterMap.get("noticeType");
        //创建爬虫
        spider = Spider.create(new NoticePageProcessor()).addPipeline(new NoticePipeline()).thread(threadNum);
        if (noticeType.equals("不分类")) {
            spider.addUrl("http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=104&pgs=15&pgi=0")
                    .addUrl("http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=103&pgs=15&pgi=0");
        } else if (noticeType.equals("申请公告")) {
            spider.addUrl("http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=103&pgs=15&pgi=0");
        } else {
            spider.addUrl("http://www.nybkjfzzx.cn/Handler/ArticlesHandler.ashx?OP=getpagekjdatas&nid=104&pgs=15&pgi=0");
        }
        return spider;
    }

    /**
     * 启动申请/授权公告详情爬虫
     */
    public Spider startNoticeDetailSpider(Spider spider, HashMap<String, String> parameterMap) {
        //获取线程数
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        //查看爬虫精确类型
        String accuracy = parameterMap.get("accuracy");
        //获取公告类型
        String noticeType = parameterMap.get("noticeType");
        //创建爬虫，指定解析器等参数
        spider = Spider.create(new NoticeDetailPageProcessor()).addPipeline(new NoticeDetailPipeline()).thread(threadNum);
        if (accuracy.equals("0")) {
            if (noticeType.equals("申请公告")) {
                List<String> vnameList = NoticeDetailDb.getAppVrName();
                for (String vname : vnameList) {
                    String url = "http://202.127.42.47:6009/Home/PostCropDetail?cropname=" + vname + "&type=1";
                    spider.addUrl(url);
                }
            } else {
                List<String> vrNameList = NoticeDetailDb.getGrantVrName();
                for (String vrName : vrNameList) {
                    String url = "http://202.127.42.47:6009/Home/PostCropDetail?cropname=" + vrName + "&type=2";
                    spider.addUrl(url);
                }
            }
        } else {
            //获取品种名称
            String cropName = parameterMap.get("cropName");
            if (noticeType.equals("申请公告")) {
                String url = "http://202.127.42.47:6009/Home/PostCropDetail?cropname=" + cropName + "&type=1";
                spider.addUrl(url);
            } else {
                String url = "http://202.127.42.47:6009/Home/PostCropDetail?cropname=" + cropName + "&type=2";
                spider.addUrl(url);
            }
        }
        return spider;
    }

    /**
     * 启动农业新闻爬虫
     */
    public Spider startNewsSpider(Spider spider, HashMap<String, String> parameterMap) {
        //获取线程数
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        //获取关键词
        String newsWord = parameterMap.get("newsWord");
        //创建爬虫
        spider = Spider.create(new NewsPageProcessor()).addPipeline(new NewsPipeline()).thread(threadNum);
        //生成url
        for (int i = 0; i <= 100; i = i + 10) {
            String url = "https://www.baidu.com/s?tn=news&rtt=4&bsst=1&cl=2&wd=" + newsWord + "&tngroupname=organic_news&rsv_dl=news_b_pn&pn=" + i;
            spider.addUrl(url);
        }
        return spider;
    }

    /**
     * 启动专利爬虫
     */
    public Spider startPatentSpider(Spider spider, HashMap<String, String> parameterMap) {
        //获取线程数
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        spider = Spider.create(new PatentPageProcessor()).addPipeline(new PatentPipeline()).thread(threadNum);
        //判断精确类型
        String accuracy = parameterMap.get("accuracy");
        if (accuracy.equals("0")) {
            List<Expert> expertList = PaternDb.getExpertList();
            for (Expert expert : expertList) {
                // 组装表单
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("CurrentQuery",
                        "F XX (" + expert.getWorkplace() + "/PA)*(" + expert.getName() + "/IN)*(34/CO)");
                params.put("OrderBy", "AD");
                params.put("OrderByType", "DESC");
                params.put("PageNum", "1");
                params.put("SessionID", "1");
                params.put("DBType", "CN");
                params.put("RowCount", "50");
                params.put("Filter", "{\"CO\":\"\",\"PT\":\"\",\"LG\":\"\"}");
                Request request = new Request("http://cprs.patentstar.com.cn/Search/SearchByQuery");
                request.setMethod(HttpConstant.Method.POST);
                request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
                spider.addRequest(request);
            }
        } else {
            //获取发明人,专利标题，工作地点
            String zlmc = parameterMap.get("zlmc");
            String fmr = parameterMap.get("fmr");
            String sqr = parameterMap.get("sqr");
            String queryStr;
            if (fmr.equals("")) {
                queryStr = "F XX (" + zlmc + "/TI)*(" + sqr + "/PA)*(34/CO)";
            } else if (sqr.equals("")) {
                queryStr = "F XX (水稻/TI)*(" + fmr + "/IN)*(34/CO)";
            } else if (sqr.equals("") && fmr.equals("")) {
                queryStr = "F XX (" + zlmc + "/TI)*(34/CO)";
            } else {
                queryStr = "F XX (" + zlmc + "/TI)*(" + sqr + "/PA)*(" + fmr + "/IN)*(34/CO)";
            }
            //组装表单
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("CurrentQuery", queryStr);
            params.put("OrderBy", "AD");
            params.put("OrderByType", "DESC");
            params.put("PageNum", "1");
            params.put("SessionID", "1");
            params.put("DBType", "CN");
            params.put("RowCount", "50");
            params.put("Filter", "{\"CO\":\"\",\"PT\":\"\",\"LG\":\"\"}");
            Request request = new Request("http://cprs.patentstar.com.cn/Search/SearchByQuery");
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
            spider.addRequest(request);
        }
        return spider;
    }

    /**
     * 启动审定品种爬虫
     */
    public Spider startVarietySpider(Spider spider, HashMap<String, String> parameterMap) {
        //获取线程数
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        //判断精确类型
        String accuracy = parameterMap.get("accuracy");
        //链接
        String targetUrl;
        if (accuracy.equals("0")) {
            targetUrl = "http://202.127.42.47:6010/SDSite/Home/GetVarietyAuthorizeMainSearch?_search=false&rows=20&sidx=JudgementYear&sord=desc&page=";
            VarietyPageProcessor varietyPageProcessor = new VarietyPageProcessor(targetUrl);
            spider = Spider.create(varietyPageProcessor).addPipeline(new VarietyPipeline()).addUrl(targetUrl + 1).thread(threadNum);
        } else {
            //获取审定编号
            String judgementNo = parameterMap.get("JudgementNo");
            String varietyNameLike = parameterMap.get("VarietyNameLike");
            String JudgementYear = parameterMap.get("JudgementYear");
            String ApplyCompanyLike = parameterMap.get("ApplyCompanyLike");
            String CropID = parameterMap.get("CropID");
            String isTransgenosis = parameterMap.get("TransgenosisFlag");
            String JudgementRegionID = parameterMap.get("JudgementRegionID");
            targetUrl = "http://202.127.42.47:6010/SDSite/Home/GetVarietyAuthorizeMainSearch?_search=false&rows=20&sidx=JudgementYear&sord=desc" + "&JudgementNo="
                    + judgementNo + "&varietyNameLike=" + varietyNameLike + "&JudgementYear=" + JudgementYear + "&ApplyCompanyLike=" + ApplyCompanyLike
                    + "&CropID=" + CropID + "&isTransgenosis=" + isTransgenosis + "&JudgementRegionID=" + JudgementRegionID + "&page=";
            VarietyPageProcessor varietyPageProcessor = new VarietyPageProcessor(targetUrl);
            spider = Spider.create(varietyPageProcessor).addPipeline(new VarietyPipeline()).addUrl(targetUrl + 1).thread(threadNum);
        }
        return spider;
    }

    /**
     * 启动品种权爬虫
     */
    public Spider startVrSpider(Spider spider, HashMap<String, String> parameterMap) {
        String name = parameterMap.get("name");      //品种名称
        String appCode = parameterMap.get("appCode");//申请号
        String appaddress = parameterMap.get("appaddress"); //申请地区
        String applier = parameterMap.get("applier");
        String type = parameterMap.get("type"); //2-->申请公告，3-->授权公告
        String accuracy = parameterMap.get("accuracy");
        int threadNum = Integer.parseInt(parameterMap.get("threadNum"));
        String url;
        if (accuracy.equals("0")) {
            url = "http://202.127.42.47:6009/Home/GetBigScreenlist?_search=false&rows=15&sidx=Type&sord=asc&type=1&page=";
            spider = Spider.create(new VrPageProcessor(url)).addUrl(url + 1).addPipeline(new VarietyRightPipeline()).thread(threadNum);
        } else {
            url = "http://202.127.42.47:6009/Home/GetBigScreenlist?_search=false&rows=15&sidx=Type&sord=asc&name=" + name + "&appCode=" +
                    appCode + "&appaddress=" + appaddress + "&applier=" + applier + "&type=" + type + "&page=";
            spider = Spider.create(new VrPageProcessor(url)).addUrl(url + 1).addPipeline(new VarietyRightPipeline()).thread(threadNum);
        }
        return spider;
    }

    /**
     * 启动代理爬虫
     *
     * @param spider
     * @return
     */
    public Spider startProxySpider(Spider spider) {
        spider = Spider.create(new ProxyPageProcessor()).addPipeline(new ProxyPipeline()).thread(1);
        for (int i = 24; i <= 100; i++) {
            spider.addUrl("https://www.xicidaili.com/nn/" + i);
        }
        return spider;
    }

    /**
     * 启动代理校验爬虫
     *
     * @param spider
     * @return
     */
    public void startProxyCheckSpider(Spider spider) {
        //给线程资源中添加数据
        ProxySource proxySource = new ProxySource();
        proxySource.addProxy();
        //创建任务
        ProxyCheckTask task = new ProxyCheckTask(proxySource);

        //创建线程池,固定大小为10
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(task);
        }
        pool.shutdown();
    }
}
