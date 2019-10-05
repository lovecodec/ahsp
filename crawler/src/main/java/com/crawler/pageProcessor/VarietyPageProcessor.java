package com.crawler.pageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.crawler.pipeline.VarietyPipeline;
import com.crawler.po.Variety;
import com.crawler.tools.RegexUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Dr.chen
 * @date 2019年04月24日
 * @Description:品种审定爬虫:数据源--种业大数据平台
 */
public class VarietyPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

    private String url;

    public VarietyPageProcessor(String url) {
        this.url = url;
    }

    @Override
    public void process(Page page) {
        // 如果是第一次访问，那么就要去添加targetUrl
        if (page.getUrl().toString().contains("page=1")) {
            int depth = Integer.parseInt(page.getHtml().regex("\"total\":\\d+").regex("\\d+").get());
            if (depth >= 2) {
                for (int i = 2; i <= depth; i++) {
                    String targetUrl = this.url + i;
                    page.addTargetRequest(targetUrl);
                }
            }
        }
        // 解析数据，生成Variety对象
        String jsonData = page.getHtml().toString();
        // 对象集合
        List<Variety> vList = new ArrayList<>();
        // 数据集合
        List<String> judgementNoList = RegexUtils.getValueList(jsonData, "JudgementNo", 1);
        List<String> vnameList = RegexUtils.getValueList(jsonData, "VarietyName", 1);
        List<String> judgementYearList = RegexUtils.getValueList(jsonData, "JudgementYear", 2);
        List<String> typeList = RegexUtils.getValueList(jsonData, "CropID", 1);
        List<String> judgeIdList = RegexUtils.getValueList(jsonData, "JudgementRegionID", 1);
        List<String> isTransgenosisList = RegexUtils.getValueList(jsonData, "IsTransgenosis", 1);
        List<String> appCompanyList = RegexUtils.getValueList(jsonData, "ApplyCompany", 1);
        List<String> announcementIDList = RegexUtils.getValueList(jsonData, "AnnouncementID", 1);

        for (int i = 0; i < judgementNoList.size(); i++) {
            Variety v = new Variety();
            v.setVarietyId(UUID.randomUUID().toString());
            v.setJudgementNo(judgementNoList.get(i));
            v.setVname(vnameList.get(i));
            v.setJudgementYear(judgementYearList.get(i));
            v.setType(typeList.get(i));
            v.setJudgeId(judgeIdList.get(i));
            v.setIsTransgenosis(isTransgenosisList.get(i));
            v.setAppCompany(appCompanyList.get(i));
            v.setAnnouncementID(announcementIDList.get(i));
            vList.add(v);
        }
        page.putField("vList", vList);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String targetUrl = "http://202.127.42.47:6010/SDSite/Home/GetVarietyAuthorizeMainSearch?_search=false&rows=20&sidx=JudgementYear&sord=desc&page=";
        VarietyPageProcessor varietyPageProcessor = new VarietyPageProcessor(targetUrl);
        Spider.create(varietyPageProcessor).addPipeline(new VarietyPipeline()).addUrl(targetUrl + 1).thread(10).run();
    }
}
