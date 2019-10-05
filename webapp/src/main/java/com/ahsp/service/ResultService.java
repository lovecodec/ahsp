package com.ahsp.service;

import com.ahsp.po.*;

/**
 * @author：Dr.chen
 * @date：2019/8/20 21:22
 * @Description：
 */
public interface ResultService {
    PageBean<Expert> getExpertByCrawler(int pc,int ps,int state) throws Exception;

    PageBean<Article> getArticleByCrawler(int pc, int ps,int state) throws Exception;

    PageBean<Notice> getNoticeByCrawler(int pc,int ps,int state) throws Exception;

    PageBean<News> getNewsByCrawler(int pc, int ps,int state) throws Exception;

    PageBean<Patent> getPatentByCrawler(int pc,int ps,int state) throws Exception;

    PageBean<AppNoticeDetails> getNoticeDetailByCrawler(int pc,int ps,int state) throws Exception;

    PageBean<Variety> getVarietyByCrawler(int pc,int ps,int state) throws Exception;

    PageBean<VarietyRight> getVrByCrawler(int pc,int ps,int state) throws Exception;

    void unionCache(int spiderType) throws Exception;

    Message removeExpertCache(int state);

    Message removePatentCache(int state);

    Message removeArticleCache(int state);

    Message removeNoticeCache(int state);

    Message removeNewsCache(int state);

    Message removeVarietyCache(int state);

    PageBean<MyProxy> getCheckedProxy(int pc,int ps) throws Exception;

    PageBean<MyProxy> getProxy(int pc,int ps) throws Exception;
}
