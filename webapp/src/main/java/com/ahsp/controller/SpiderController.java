package com.ahsp.controller;

import com.ahsp.po.*;
import com.ahsp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author : Dr.chen
 * 爬虫管理
 */
@Controller
public class SpiderController {
    @Autowired
    @Qualifier("spiderService")
    private SpiderService spiderService;

    @Autowired
    @Qualifier("resultService")
    private ResultService resultService;


    @RequestMapping("/getSpiderStatus")
    @ResponseBody
    public List<SpiderStatus> getSpiderStatus() throws  Exception{
        return spiderService.getSpiderStatus();
    }

    @RequestMapping("/startSpider")
    @ResponseBody
    public Message startSpider(@RequestParam HashMap<String, String> parameterMap) throws Exception{
        //更改状态，先获取爬虫类型
        int spiderType = Integer.parseInt(parameterMap.get("spiderType"));
        //调用更新方法
        resultService.unionCache(spiderType);
        //调用爬虫启动方法
        return spiderService.startSpider(parameterMap);
    }

    @RequestMapping("/stopSpider")
    @ResponseBody
    public Message stopSpider() throws Exception{
        return spiderService.stopSpider();
    }

    @RequestMapping("/getSpiderStatusById")
    @ResponseBody
    public SpiderStatus getSpiderStatusById(int spider_id) throws Exception{
        return spiderService.getSpiderStatusById(spider_id);
    }

}
