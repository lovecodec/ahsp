package com.crawler.tools;


import com.crawler.po.Article;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BdxsTools {
	/**
	 * 获取参数，用于筛选出key=value的数据
	 * 
	 * @param html
	 * @param regex
	 * @return
	 */
	public static String getParam(String html, String regex) {
		String param = "";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(html);
		while (matcher.find()) {
			String[] split = matcher.group().split("=");
			param = split[1].trim().replace("\"", "").replace("\"", "");
		}
		return param;
	}

	/**
	 * 获取形如key:'value'类型的参数的值
	 */
	public static String getParamValue(String html, String regex) {
		String param = "";
		Pattern p = Pattern.compile("entity_id: '.*?'");
		Matcher matcher = p.matcher(html);
		while (matcher.find()) {
			String[] split = matcher.group().split(":");
			param = split[1].trim().replaceAll("\'", "");
		}
		return param;

	}

	/**
	 * 获取UNIX时间戳
	 */
	public static int getTimeStamp() {
		int time = (int) (System.currentTimeMillis() / 1000);
		return time;
	}

	// 获取_sign参数
	public static String getSign(String token, String time) {
		String sign = time + token + "3b6e2e87fcce6eea8337eeac83b478f5";
		String _sign = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sign.getBytes(Charset.forName("utf-8")));
			byte[] md5Bytes = md.digest();
			_sign = Hex.encodeHexString(md5Bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _sign;
	}

	// 发送post请求 获取url集合-->发现安徽省的专家
	public static List<String> doPost(String html) {
		List<String> urlList = new ArrayList<String>();

		List<NameValuePair> form = new ArrayList<NameValuePair>();
		// 获取token参数
		String token = getParam(html, "bds.cf.token = \".*?\"");
		String timestamp = getTimeStamp() + "";
		form.add(new BasicNameValuePair("_token", token));
		form.add(new BasicNameValuePair("_ts", timestamp));
		form.add(new BasicNameValuePair("cmd", "show_co_affiliate"));
		form.add(new BasicNameValuePair("_sign", getSign(token, timestamp)));
		form.add(new BasicNameValuePair("entity_id", getParamValue(html, "entity_id: '.*?'")));
		String content = HttpUtils.sendPost("http://xueshu.baidu.com/usercenter/data/author", form).trim();

		urlList = getList(content);

		return urlList;
	}

	// 筛选出需要的专家信息-->安徽省的专家信息
	public static List<String> getList(String text) {
		List<String> list = new ArrayList<String>();
		List<String> tempList = new ArrayList<String>();
		Pattern p = Pattern.compile("<a.*?>.*?</a>");
		Matcher m = p.matcher(text);
		while (m.find()) {
			String temp = m.group();
			if (temp.contains("安徽")) {
				tempList.add(temp);
			}
		}
		// 再进行正则，将链接提取出来
		p = Pattern.compile("href=\".*?\"");
		for (String tempResult : tempList) {
			m = p.matcher(tempResult);
			while (m.find()) {
				String temp = m.group();
				String[] split = temp.split("=");
				list.add("http://xueshu.baidu.com" + split[1].trim().replaceAll("\"", ""));
			}
		}
		return list;
	}

	/**
	 * 发送post请求，获取专家论文信息
	 * 
	 * @param html
	 * @return
	 */
	public static List<Article> getArticleList(String html) {
		List<Article> list = new ArrayList<>();
		List<NameValuePair> form = new ArrayList<>();
		form = setCommonParam(form, html);
		form.add(new BasicNameValuePair("curPageNum", "1"));
		String content = HttpUtils.sendPost("http://xueshu.baidu.com/usercenter/data/author", form).trim();
		int depth = getDepth(content);
		if (depth == 1) {
			list = getArticalList(content, list);
			return list;
		} else {
			list = getArticalList(content, list);
			// 再次发送请求
			for (int i = 2; i <= depth; i++) {
				form = setCommonParam(form, html);
				form.add(new BasicNameValuePair("curPageNum", i + ""));
				String pageSource = HttpUtils.sendPost("http://xueshu.baidu.com/usercenter/data/author", form).trim();
				list = getArticalList(pageSource, list);
			}
			return list;
		}
	}

	/**
	 * 给表单设置相同的参数，避免代码冗余
	 * 
	 * @param form
	 * @param html
	 * @return form
	 */
	private static List<NameValuePair> setCommonParam(List<NameValuePair> form, String html) {
		String timestamp = getTimeStamp() + "";
		form.add(new BasicNameValuePair("_token", getParam(html, "bds.cf.token = \".*?\"")));
		form.add(new BasicNameValuePair("_ts", timestamp));
		form.add(new BasicNameValuePair("_sign", getParam(html, "bds.cf.sign = \".*?\"")));
		form.add(new BasicNameValuePair("cmd", "academic_paper"));
		form.add(new BasicNameValuePair("entity_id", getParamValue(html, "entity_id: '.*?'")));
		form.add(new BasicNameValuePair("bsToken", getParamValue(html, "bsToken: '.*?'")));
		form.add(new BasicNameValuePair("sc_sort", "sc_time"));
		form.add(new BasicNameValuePair("first_author", "1"));
		return form;
	}

	/**
	 * 获取百度学者下，学者论文的页数
	 * 
	 * @param html
	 * @return depth
	 */
	private static int getDepth(String html) {
		List<Integer> list = new ArrayList<Integer>();
		Document dom = Jsoup.parse(html);
		Elements pages = dom.getElementsByClass("res-page-number pagenumber");
		for (Element page : pages) {
			list.add(Integer.parseInt(page.text()));
		}
		int max = 1;

		for (int i = 0; i < list.size(); i++) {
			if (i > max)
				max = i;
		}
		return max;
	}

	/**
	 * 通过jsoup去解析，获取作者文章
	 * 
	 * @param html
	 * @param list
	 * @return
	 */
	private static List<Article> getArticalList(String html, List<Article> list) {
		Document dom = Jsoup.parse(html);
		Elements results = dom.getElementsByClass("result");
		for (Element result : results) {
			Article article = new Article();
			String info = result.getElementsByClass("res_info").text();
			article.setAid(UUID.randomUUID().toString().replaceAll("-", ""));
			article.setInfo(info);
			article.setTitle(result.getElementsByClass("res_t").text());
			article.setYear(result.getElementsByClass("res_year").text());
			article.setUrl(result.getElementsByClass("reqdata").attr("data-url"));
			// 使用正则表达式，筛选出发表机构
			Pattern p = Pattern.compile("《.*?》");
			Matcher m = p.matcher(info);
			while (m.find()) {
				article.setPubPlace(m.group());
			}
			list.add(article);
		}
		return list;
	}
}
