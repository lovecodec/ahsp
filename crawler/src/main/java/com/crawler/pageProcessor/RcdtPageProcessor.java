package com.crawler.pageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.crawler.pipeline.RcdtPipeline;
import com.crawler.po.Expert;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

/**
 * 
 * @author Dr.chen
 * @date 2019年05月06日
 * @Description:人才地图获取专家数据
 */
public class RcdtPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
			.addHeader("Cookie",
					"JSESSIONID=1D9CA469446BC7AD598EED62A0A1212D; UM_distinctid=169c47f7dec9-09f518443abf898-4c312c7c-144000-169c47f7dee43e; cookiesession1=12ECE520OGCOFUONPSA1QUSW1F0MD7A0")
			.addHeader("Referer", "http://app.ahau.edu.cn/pi/manage/jbxxSearchHz.jsp")
			.addHeader("X-Requested-With", "XMLHttpRequest").addHeader("Accept-Encoding", "gzip, deflate")
			.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");

	@Override
	public void process(Page page) {
		List<String> nameList = new JsonPathSelector("$.rows[*].xm").selectList(page.getRawText());
		List<String> workplaceList = new JsonPathSelector("$.rows[*].gzdw").selectList(page.getRawText());
		List<Expert> expertList = new ArrayList<Expert>();
		for (int i = 0; i < nameList.size(); i++) {
			Expert expert = new Expert();
			expert.setEid(UUID.randomUUID().toString().replaceAll("-", ""));
			expert.setName(nameList.get(i));
			expert.setWorkplace(workplaceList.get(i));
			expert.setDomain("作物遗传育种");
			expert.setGather("种业人才地图");
			expertList.add(expert);
		}
		page.putField("expertList", expertList);
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider spider = Spider.create(new RcdtPageProcessor());
		for (int i = 1; i <= 11; i++) {
			Request request = new Request(
					"http://app.ahau.edu.cn/pi/json/jbxxSearch.action?organizationno=340000&yjly=10&yjdx=&yjfx=&xstx=&xm=&gzdw=&page="
							+ i + "&rows=25");
			request.setMethod(HttpConstant.Method.POST);
			spider.addRequest(request);
		}
		spider.addPipeline(new RcdtPipeline()).thread(5).run();
	}
}
