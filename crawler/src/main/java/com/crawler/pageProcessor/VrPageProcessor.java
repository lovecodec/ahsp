package com.crawler.pageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.crawler.pipeline.VarietyRightPipeline;
import com.crawler.po.VarietyRight;
import com.crawler.tools.RegexUtils;
import com.crawler.tools.SpiderTools;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @author Dr.chen
 * @date 2019年04月23日
 * @Description:品种权爬虫 网站:种业大数据平台
 */
public class VrPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(15000).addHeader("User-Agent",
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

	private String url;
	public VrPageProcessor(String url){
		this.url = url;
	}
	@Override
	public void process(Page page) {
		// 如果是第一次访问，那么就要去添加targetUrl
		if (page.getUrl().toString().contains("page=1")) {
			// 获取总页数
			int depth = Integer.parseInt(page.getHtml().regex("\"total\":\\d+").regex("\\d+").get());
			for (int i = 2; i <= depth; i++) {
				page.addTargetRequest(this.url + i);
			}
		}
		// 解析数据，生成varietyRight对象
		String jsonData = page.getHtml().toString();
		// 对象集合
		List<VarietyRight> vrList = new ArrayList<VarietyRight>();
		// 数据集合
		List<String> appCodeList = RegexUtils.getValueList(jsonData, "AppCode", 1);
		List<String> grantCodeList = RegexUtils.getValueList(jsonData, "GrantCode", 1);
		List<String> typeList = RegexUtils.getValueList(jsonData, "CnName", 1);
		List<String> vnameList = RegexUtils.getValueList(jsonData, "TempName", 1);
		List<String> appDateList = RegexUtils.getValueList(jsonData, "AppDate", 1);
		List<String> ownerList = RegexUtils.getValueList(jsonData, "Owner", 1);
		List<String> pubNoList = RegexUtils.getValueList(jsonData, "PubNo", 1);
		List<String> noticeTypeList = RegexUtils.getValueList(jsonData, "Type", 1);
		for (int i = 0; i < appCodeList.size(); i++) {
			VarietyRight vr = new VarietyRight();
			String timestamp = SpiderTools.getNumberFromText(appDateList.get(i));
			vr.setVrId(UUID.randomUUID().toString());
			vr.setAppCode(appCodeList.get(i));
			vr.setGrantCode(grantCodeList.get(i));
			vr.setVrType(typeList.get(i));
			vr.setVrName(vnameList.get(i));
			vr.setAppDate(SpiderTools.getNormalTime(timestamp));
			vr.setOwner(ownerList.get(i));
			vr.setPubNo(pubNoList.get(i));
			vr.setNoticeType(noticeTypeList.get(i));
			vrList.add(vr);
		}
		page.putField("vrList", vrList);
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		String url = "http://202.127.42.47:6009/Home/GetBigScreenlist?_search=false&rows=15&sidx=Type&sord=asc&type=3&page=";
		Spider.create(new VrPageProcessor(url)).addUrl(url + 1).addPipeline(new VarietyRightPipeline()).thread(5).run();
	}
}
