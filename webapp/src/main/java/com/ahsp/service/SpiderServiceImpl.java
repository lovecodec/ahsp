package com.ahsp.service;

import com.ahsp.mapper.SpiderMapper;
import com.ahsp.po.Message;
import com.ahsp.po.SpiderStatus;
import com.ahsp.utils.CommonUtils;
import com.crawler.starter.SpiderStarter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.io.*;
import java.util.HashMap;
import java.util.List;

@Service("spiderService")
public class SpiderServiceImpl implements SpiderService{
    @Autowired
    private SpiderMapper spiderMapper;

    private Spider spider;

    @Override
    public List<SpiderStatus> getSpiderStatus() throws Exception {
        //在service层完成拼装
        return spiderMapper.getSpiderStatus();
    }

    @Override
    public SpiderStatus getSpiderStatusById(int spider_id) throws Exception {
        return spiderMapper.getSpiderStatusById(spider_id);
    }

    @Override
    public Message startSpider(HashMap<String,String> parameterMap) throws Exception {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"爬虫启动信息!","爬虫已启动,你可以在导航栏【爬取结果】查看当前爬虫的爬取结果。\n");
        int spiderType = Integer.parseInt(parameterMap.get("spiderType"));
        //爬虫启动时更新爬虫这次的运行时间
        spiderMapper.updateSpiderStatus(spiderType,CommonUtils.getCurrentTime());
        SpiderStarter spiderStarter = new SpiderStarter();
        //根据类型来启动爬虫
        switch (spiderType) {
            case 1 :
                spider = spiderStarter.startBdxsSpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 2:
                spider = spiderStarter.startNoticeSpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 3:
                spider = spiderStarter.startCnkiSpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 4:
                spider = spiderStarter.startNoticeDetailSpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 5:
                spider = spiderStarter.startNewsSpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 6:
                spider = spiderStarter.startPatentSpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 7:
                spider = spiderStarter.startVarietySpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 8:
                spider = spiderStarter.startVrSpider(spider,parameterMap);
                spider.runAsync();
                break;
            case 9:
                spider = spiderStarter.startProxySpider(spider);
                spider.runAsync();
                break;
            case 10:
                spiderStarter.startProxyCheckSpider(spider);
                break;
        }
        return message;
    }

    @Override
    public Message stopSpider() throws Exception{
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"爬虫停止信息!","");
        if(spider==null){
            message.setMessage_content("爬虫没有启动，无需关闭，请重试。");
            return message;
        }
        message.setMessage_content("爬虫已停止........\n");
        spider.stop();
        return message;
    }

}
