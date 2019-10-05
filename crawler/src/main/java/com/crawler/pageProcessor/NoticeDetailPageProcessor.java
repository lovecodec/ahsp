package com.crawler.pageProcessor;

import java.util.List;
import java.util.UUID;


import com.crawler.common.NoticeDetailDb;
import com.crawler.pipeline.NoticeDetailPipeline;
import com.crawler.po.AppNoticeDetails;
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
 * @Description:品种申请公告详情爬虫
 */
public class NoticeDetailPageProcessor implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader("User-Agent",
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

	@Override
	public void process(Page page) {
		//解析出数据
		String jsonData = page.getHtml().toString();
		AppNoticeDetails apd = new AppNoticeDetails();
		String appDate = SpiderTools.getNormalTime(SpiderTools.getNumberFromText(RegexUtils.getValue(jsonData, "AppDate")));
		String grantDate = SpiderTools.getNormalTime(SpiderTools.getNumberFromText(RegexUtils.getValue(jsonData, "GrantDate")));
		String pubDate = SpiderTools.getNormalTime(SpiderTools.getNumberFromText(RegexUtils.getValue(jsonData, "PubDate")));
		apd.setNoticeDetailId(UUID.randomUUID().toString().replaceAll("-",""));
		apd.setCropName(RegexUtils.getValue(jsonData, "CropName"));
		apd.setBreedName(RegexUtils.getValue(jsonData, "BreedName"));
		apd.setAppNo(RegexUtils.getValue(jsonData, "AppNo"));
		apd.setAppDate(appDate);
		apd.setApplicant(RegexUtils.getValue(jsonData, "Applicant"));
		apd.setType(RegexUtils.getValue(jsonData, "Type"));
		apd.setState(RegexUtils.getValue(jsonData, "State"));
		apd.setGrantDate(grantDate);
		apd.setGrantNo(RegexUtils.getValue(jsonData, "GrantNo"));
		apd.setPubDate(pubDate);
		apd.setPubNo(RegexUtils.getValue(jsonData, "PubNo"));
		apd.setOwner(RegexUtils.getValue(jsonData, "Owner"));
		apd.setOwnerAddress(RegexUtils.getValue(jsonData, "OwnerAddress"));
		page.putField("apd", apd);
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider spider = Spider.create(new NoticeDetailPageProcessor()).addPipeline(new NoticeDetailPipeline());
		String url = "http://202.127.42.47:6009/Home/PostCropDetail?cropname=" + "桂云3号" + "&type=1";
		spider.addUrl(url);
		spider.thread(1).run();

		/**授权公告详情
		 * List<String> vrNameList = NoticeDetailDb.getGrantVrName();
		 Spider spider = Spider.create(new GrtNotDetailsPageProcessor()).addPipeline(new NoticeDetailPipeline());
		 for(String vrName : vrNameList){
		 String url = "http://202.127.42.47:6009/Home/PostCropDetail?cropname=" + vrName + "&type=2";
		 spider.addUrl(url);
		 }
		 */
	}
}
