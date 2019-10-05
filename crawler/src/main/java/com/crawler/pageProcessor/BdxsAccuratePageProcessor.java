package com.crawler.pageProcessor;

import com.crawler.po.Expert;
import com.crawler.tools.BdxsTools;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.UUID;

//精确获取某个专家的信息
public class BdxsAccuratePageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");

    @Override
    public void process(Page page) {
        //进入搜索结果界面
        if (page.getUrl().toString().contains("http://xueshu.baidu.com/usercenter/data/authorchannel?cmd=inject_page")) {
            String author = page.getUrl().regex("author=.*?&").get();
            String workplace = page.getUrl().regex("affiliate=.*").get();
            //获取token,获取sign
            String token = BdxsTools.getParam(page.getRawText(), "bds.cf.token = \".*?\"");
            String sign = BdxsTools.getParam(page.getRawText(), "bds.cf.sign = \".*?\"");
            //构造结果页链接
            String resultUrl = "http://xueshu.baidu.com/usercenter/data/authorchannel?cmd=search_author&_token=" + token + "&_sign=" + sign + "&"
                    + author + workplace + "&curPageNum=1";
            page.addTargetRequest(resultUrl);
        }else if(page.getUrl().toString().contains("http://xueshu.baidu.com/usercenter/data/authorchannel?cmd=search_author")){
            //获取到结果
            List<String> all = page.getHtml().regex("href=.*?homepage.*?\\\"").all();
            String url = all.get(0).replaceAll("&quot;","").replaceAll("\\\\","").replaceAll("href=","").replaceAll("\"","");
            url = "http://xueshu.baidu.com" + url;
            page.addTargetRequest(url);
        }else{
            //解析当前页面，获取专家信息
            String domainCss = ".person_domain > a:nth-child(1)";//获取专家研究领域
            String expertNameCss = ".p_name";  //专家名称
            String workplaceCss = ".p_affiliate";	//专家工作点
            Document dom = page.getHtml().getDocument();
            String domain = dom.select(domainCss).text();
            Expert expert = new Expert();
            String eid = UUID.randomUUID().toString().replaceAll("-", "");
            expert.setEid(eid);
            expert.setName(dom.select(expertNameCss).text());
            expert.setWorkplace(dom.select(workplaceCss).text());
            expert.setDomain(domain);
            expert.setGather("百度学术");
            page.putField("expert", expert);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String s = "http://xueshu.baidu.com/usercenter/data/authorchannel?cmd=inject_page&author=%E5%90%B4%E7%88%BD&affiliate=%E5%AE%89%E5%BE%BD%E7%9C%81%E5%86%9C%E4%B8%9A%E7%A7%91%E5%AD%A6%E9%99%A2";
        Spider.create(new BdxsAccuratePageProcessor()).addUrl(s).thread(1).run();
    }
}
