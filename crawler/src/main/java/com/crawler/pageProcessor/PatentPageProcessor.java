package com.crawler.pageProcessor;

import java.util.*;
import com.crawler.po.Patent;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;


public class PatentPageProcessor implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(5000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
			.addHeader("X-Requested-With", "XMLHttpRequest");

	public PatentPageProcessor(){
		String cookie = login();
		this.site.addHeader("Cookie",cookie);
	}
	@Override
	public void process(Page page) {
		//存放专利的集合
		List<Patent> patentList = new LinkedList<Patent>();
		// 解析出总记录数，如果没有该记录那么就选择跳过筛选阶段
		String depth = new JsonPathSelector("$.Data.HitCount").select(page.getRawText());
		if (!depth.equals("0")) {
			// 专利名列表
			List<String> zlmcList = new JsonPathSelector("$.Data.List[*].TI").selectList(page.getRawText());
			// 申请号列表
			List<String> sqhList = new JsonPathSelector("$.Data.List[*].AN").selectList(page.getRawText());
			// 申请日列表
			List<String> sqrList = new JsonPathSelector("$.Data.List[*].AD").selectList(page.getRawText());
			// 申请省市列表
			List<String> sqssList = new JsonPathSelector("$.Data.List[*].CO").selectList(page.getRawText());
			// 公开日列表
			List<String> gkrList = new JsonPathSelector("$.Data.List[*].PD").selectList(page.getRawText());
			// 公开号列表
			List<String> gkhList = new JsonPathSelector("$.Data.List[*].PN").selectList(page.getRawText());
			// 授权公告号列表
			List<String> sqgghList = new JsonPathSelector("$.Data.List[*].GN").selectList(page.getRawText());
			// 授权公告日列表
			List<String> sqggrList = new JsonPathSelector("$.Data.List[*].GD").selectList(page.getRawText());
			// 主分类号列表
			List<String> zflhList = new JsonPathSelector("$.Data.List[*].MC").selectList(page.getRawText());
			// 分类号列表
			List<String> flhList = new JsonPathSelector("$.Data.List[*].IC").selectList(page.getRawText());
			// 第一发明人列表
			List<String> dyfmrList = new JsonPathSelector("$.Data.List[*].FI").selectList(page.getRawText());
			// 申请人列表
			List<String> sqrenList = new JsonPathSelector("$.Data.List[*].PA").selectList(page.getRawText());
			// 申请人地址列表
			List<String> sqrdzList = new JsonPathSelector("$.Data.List[*].DZ").selectList(page.getRawText());
			// 发明人列表
			List<String> fmrList = new JsonPathSelector("$.Data.List[*].IN").selectList(page.getRawText());
			// 当前权利人列表
			List<String> dqqlrList = new JsonPathSelector("$.Data.List[*].PE").selectList(page.getRawText());
			// 代理人列表
			List<String> dlrList = new JsonPathSelector("$.Data.List[*].AT").selectList(page.getRawText());
			// 代理机构列表
			List<String> dljgList = new JsonPathSelector("$.Data.List[*].AG").selectList(page.getRawText());
			// 优先权列表
			List<String> yxqList = new JsonPathSelector("$.Data.List[*].PR").selectList(page.getRawText());
			// 范畴分类列表
			List<String> fcflList = new JsonPathSelector("$.Data.List[*].CT").selectList(page.getRawText());
			// 状态列表
			List<String> ztList = new JsonPathSelector("$.Data.List[*].LG").selectList(page.getRawText());
			// 摘要列表
			List<String> zyList = new JsonPathSelector("$.Data.List[*].AB").selectList(page.getRawText());
			// 主权力要求
			List<String> zqlyqList = new JsonPathSelector("$.Data.List[*].CL").selectList(page.getRawText());
			for (int i = 0; i < zlmcList.size(); i++) {
				Patent p = new Patent();
				p.setSqh(sqhList.get(i));
				p.setSqr(sqrList.get(i));
				p.setSqss(sqssList.get(i));
				p.setGkh(gkhList.get(i));
				p.setGkr(gkrList.get(i));
				p.setSqggh(sqgghList.get(i));
				p.setSqggr(sqggrList.get(i));
				p.setZflh(zflhList.get(i));
				p.setFlh(flhList.get(i));
				p.setDyfmr(dyfmrList.get(i));
				p.setSqren(sqrenList.get(i));
				p.setDz(sqrdzList.get(i));
				p.setFmr(fmrList.get(i));
				p.setDqqlr(dqqlrList.get(i));
				p.setDlr(dlrList.get(i));
				p.setDljg(dljgList.get(i));
				p.setYxq(yxqList.get(i));
				p.setFcfl(fcflList.get(i));
				p.setGjc("无");
				p.setZt(ztList.get(i));
				p.setZy(zyList.get(i));
				p.setZqlyq(zqlyqList.get(i));
				p.setZlmc(zlmcList.get(i));
				p.setPatentId(UUID.randomUUID().toString());
				patentList.add(p);
			}
		}
		page.putField("patentList", patentList);
	}

	@Override
	public Site getSite() {
		return site;
	}

	// 模拟登陆获取cookie
	private  String login() {
		String cookie;
		String session_id = "";
		// 首先模拟登陆，获取cookie
		CloseableHttpClient hClient = HttpClients.createDefault();
		HttpPost hPost = new HttpPost("http://cprs.patentstar.com.cn/Account/UserLogin");
		// 参数
		List<NameValuePair> form = new ArrayList<NameValuePair>();
		form.add(new BasicNameValuePair("loginname", "1191180437"));
		form.add(new BasicNameValuePair("password", "chen1998"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
		hPost.setEntity(entity);
		try {
			CloseableHttpResponse response = hClient.execute(hPost);
			String temp = response.getFirstHeader("Set-Cookie").getValue();
			String[] split = temp.split(";");
			session_id = split[0];
			response.close();
			hClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		cookie = session_id + "; MobilePhone=15655677592; LoginName=1191180437";
		System.out.println("成功模拟登陆，并且生成cookie:" + cookie);
		return cookie;
	}

}