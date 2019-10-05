package com.ahsp.service;

import com.ahsp.po.Message;
import com.ahsp.po.SpiderStatus;

import java.util.HashMap;
import java.util.List;

public interface SpiderService {
    List<SpiderStatus> getSpiderStatus() throws  Exception;

    SpiderStatus getSpiderStatusById(int spider_id) throws Exception;

    Message startSpider(HashMap<String,String> parameterMap) throws Exception;

    Message stopSpider() throws Exception;

}
