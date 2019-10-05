package com.crawler.pageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.crawler.pipeline.NewsPipeline;
import com.crawler.po.News;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 
 * @author Dr.chen
 * @date 2019年05月06日
 * @Description:百度农作物新闻爬虫
 */
public class NewsPageProcessor implements PageProcessor {



	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader("User-Agent",
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");


	// 只爬取10页的内容，获取新闻的链接、出处、时间、简要内容
	@Override
	public void process(Page page) {
		/**
		 * 解析内容
		 */
		Document dom = page.getHtml().getDocument();
		/**
		 * css选择器
		 */
		String titleCss = ".c-title";
		String linkCss = ".c-title a";
		String summaryCss = ".c-summary";
		String authorCss = ".c-author";
		Elements titles = dom.select(titleCss);
		Elements links = dom.select(linkCss);
		Elements authors = dom.select(authorCss);
		Elements summary = dom.select(summaryCss);
		List<News> newsList = new ArrayList<News>();
		//创建news对象，便于数据库存储
		for(int i = 0; i < titles.size();i++) {
			News news = new News();
			String author = authors.get(i).text();
			news.setNews_title(titles.get(i).text());
			news.setNews_href(links.get(i).attr("href"));
			news.setNews_id(UUID.randomUUID().toString());
			news.setNews_author(author);
			news.setNews_summary(summary.get(i).text().replace(author, "").replace("百度快照", ""));
			news.setNews_time(getTime(author));
			newsList.add(news);
			
		}
		page.putField("newsList", newsList);
	}


	
	private String getTime(String text) {
		String time = "";
		Pattern p = Pattern.compile("\\d{4}年.*?日");
		Matcher m = p.matcher(text);
		while(m.find()) {
			time = m.group();
		}
		return time;
	}
	
	@Override
	public Site getSite() {
		return site;
	}
	

	public static void main(String[] args) {

	}
}
