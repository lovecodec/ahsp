package com.crawler.pageProcessor;


import com.crawler.pipeline.BdxsPipeline;
import com.crawler.po.Article;
import com.crawler.po.Expert;
import com.crawler.tools.BdxsTools;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.List;
import java.util.UUID;
/**
 * 
 * @author Dr.chen
 * @date 2019年05月06日
 * @Description:百度学术爬虫---发现专家爬虫
 * 新版本进行修改，不再使用百度学术爬虫获取专家论文信息，若是获取专家论文信息，需要使用知网论文爬虫
 */
public class BdxsPageProcessor implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

	@Override
	public void process(Page page) {
		String domainCss = ".person_domain > a:nth-child(1)";//获取专家研究领域
		String expertNameCss = ".p_name";  //专家名称
		String workplaceCss = ".p_affiliate";	//专家工作点
		Document dom = page.getHtml().getDocument();
		String domain = dom.select(domainCss).text();
		//创建一个expert对象
		if(domain.equals("作物遗传育种")) {
			Expert expert = new Expert();
			String eid = UUID.randomUUID().toString().replaceAll("-", "");
			expert.setEid(eid);
			expert.setName(dom.select(expertNameCss).text());
			expert.setWorkplace(dom.select(workplaceCss).text());
			expert.setDomain(domain);
			expert.setGather("百度学术");
			//发送post请求，获取专家的论文信息
			//List<Article> articleList = BdxsTools.getArticleList(page.getHtml().toString());
			//expert.setArticleList(articleList);
			page.putField("expert", expert);
		}
		//发送post请求，将获取的链接存放到targerUrl中
		try {
			List<String> urlList = BdxsTools.doPost(page.getHtml().toString());
			for (String url : urlList) {
				Request request = new Request();
				request.setMethod(HttpConstant.Method.GET);
				request.setUrl(url);
				request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");
				page.addTargetRequest(request);
			}
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Spider.create(new BdxsPageProcessor()).addUrl("http://xueshu.baidu.com/scholarID/CN-BL74C1PJ").addPipeline(new BdxsPipeline()).thread(5).run();
	}
}
