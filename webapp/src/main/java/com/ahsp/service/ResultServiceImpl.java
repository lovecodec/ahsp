package com.ahsp.service;

import com.ahsp.dao.RedisDao;
import com.ahsp.po.*;
import com.ahsp.utils.CommonUtils;
import com.ahsp.utils.RedisPageUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author：Dr.chen
 * @date：2019/8/20 21:22
 * @Description：
 */
@Service("resultService")
public class ResultServiceImpl implements ResultService {
    @Autowired
    private RedisDao redisDao;
    static Logger logger = LoggerFactory.getLogger("cacheLogger");

    private List<Expert> expertList = new ArrayList<>();
    private List<Article> articleList = new ArrayList<>();
    private List<Notice> noticeList = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();
    private List<AppNoticeDetails> apdList = new ArrayList<>();
    private List<Variety> varietyList = new ArrayList<>();
    private List<Patent> patentList = new ArrayList<>();
    private List<VarietyRight> vrList = new ArrayList<>();
    private List<MyProxy> proxyCheckedList = new ArrayList<>();
    private List<MyProxy> proxyList = new ArrayList<>();

    @Override
    public PageBean<Expert> getExpertByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.EXPERT_CACHE_1;
        if (state == 2) cacheName = RedisDao.EXPERT_CACHE_2;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<Expert> pb = new PageBean<>();
        if (totalRecords == expertList.size())
            pb.setBeanList(RedisPageUtils.pageExpertList(expertList, pc, ps));
        else {
            expertList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            //反序列化为JavaBean
            while (iterator.hasNext()) {
                expertList.add(JSON.parseObject(iterator.next(), Expert.class));
            }
            pb.setBeanList(RedisPageUtils.pageExpertList(expertList, pc, ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<Article> getArticleByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.ARTICLE_CACHE_1;
        if (state == 2) cacheName = RedisDao.ARTICLE_CACHE_2;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<Article> pb = new PageBean<>();
        if (totalRecords == articleList.size()) {
            pb.setBeanList(RedisPageUtils.pageArticleList(articleList, pc, ps));
        } else {
            articleList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                articleList.add(JSON.parseObject(iterator.next(), Article.class));
            }
            pb.setBeanList(RedisPageUtils.pageArticleList(articleList, pc, ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<Notice> getNoticeByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.NOTICE_CACHE_1;
        if (state == 2) cacheName = RedisDao.NOTICE_CACHE_2;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<Notice> pb = new PageBean<>();
        if (totalRecords == noticeList.size()) {
            pb.setBeanList(RedisPageUtils.pageNoticeList(noticeList,pc,ps));
        }else {
            noticeList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                noticeList.add(JSON.parseObject(iterator.next(), Notice.class));
            }
            pb.setBeanList(RedisPageUtils.pageNoticeList(noticeList,pc,ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<News> getNewsByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.NEWS_CACHE_1;
        if (state == 2) cacheName = RedisDao.NEWS_CACHE_2;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<News> pb = new PageBean<>();
        if (totalRecords == newsList.size()) {
            pb.setBeanList(RedisPageUtils.pageNewsList(newsList,pc,ps));
        } else {
            newsList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                newsList.add(JSON.parseObject(iterator.next(), News.class));
            }
            pb.setBeanList(RedisPageUtils.pageNewsList(newsList,pc,ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<Patent> getPatentByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.PATENT_CACHE_1;
        if (state == 2) cacheName = RedisDao.PATENT_CACHE_2;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<Patent> pb = new PageBean<>();
        if (totalRecords == patentList.size()) {
            pb.setBeanList(RedisPageUtils.pagePatentList(patentList,pc,ps));
        } else {
            patentList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                patentList.add(JSON.parseObject(iterator.next(), Patent.class));
            }
            pb.setBeanList(RedisPageUtils.pagePatentList(patentList,pc,ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<AppNoticeDetails> getNoticeDetailByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.NOTICE_DETAIL_CACHE_1;
        if (state == 2) cacheName = RedisDao.NOTICE_DETAIL_CACHE_2;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<AppNoticeDetails> pb = new PageBean<>();
        if (totalRecords == apdList.size()) {
            pb.setBeanList(RedisPageUtils.pageNoticeDetailList(apdList,pc,ps));
        } else {
            apdList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                apdList.add(JSON.parseObject(iterator.next(), AppNoticeDetails.class));
            }
            pb.setBeanList(RedisPageUtils.pageNoticeDetailList(apdList,pc,ps));
        }
        pb.setPs(ps);
        pb.setPc(pc);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<Variety> getVarietyByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.VARIETY_CACHE_1;
        if (state == 2) cacheName = RedisDao.VARIETY_CACHE_2;
        PageBean<Variety> pb = new PageBean<>();
        int totalRecords = redisDao.getResultCount(cacheName);
        if (totalRecords == varietyList.size()) {
            pb.setBeanList(RedisPageUtils.pageVarietyList(varietyList,pc,ps));
        } else {
            varietyList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                varietyList.add(JSON.parseObject(iterator.next(), Variety.class));
            }
            pb.setBeanList(RedisPageUtils.pageVarietyList(varietyList,pc,ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<VarietyRight> getVrByCrawler(int pc, int ps, int state) throws Exception {
        String cacheName = RedisDao.VR_CACHE_1;
        if (state == 2) cacheName = RedisDao.VR_CACHE_2;
        //查询总记录数
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<VarietyRight> pb = new PageBean<>();
        if (totalRecords == vrList.size()) {//相等不再去缓存中查询，直接使用List
            pb.setBeanList(RedisPageUtils.pageVrList(vrList,pc,ps));
        } else {
            //清空List，再进行添加
            vrList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                vrList.add(JSON.parseObject(iterator.next(), VarietyRight.class));
            }
            pb.setBeanList(RedisPageUtils.pageVrList(vrList,pc,ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public void unionCache(int spiderType) throws Exception {
        //根据爬虫类型进行替换，替换后清空缓存1
        switch (spiderType) {
            case 1:
                redisDao.unionCache(RedisDao.EXPERT_CACHE_2, RedisDao.EXPERT_CACHE_1, RedisDao.EXPERT_CACHE_2);
                redisDao.removeCache(RedisDao.EXPERT_CACHE_1);
                break;
            case 2:
                redisDao.unionCache(RedisDao.NOTICE_CACHE_2, RedisDao.NOTICE_CACHE_1, RedisDao.NOTICE_CACHE_2);
                redisDao.removeCache(RedisDao.NOTICE_CACHE_1);
                break;
            case 3:
                redisDao.unionCache(RedisDao.ARTICLE_CACHE_2, RedisDao.ARTICLE_CACHE_1, RedisDao.ARTICLE_CACHE_2);
                redisDao.removeCache(RedisDao.ARTICLE_CACHE_1);
                break;
            case 4:
                redisDao.unionCache(RedisDao.NOTICE_DETAIL_CACHE_2, RedisDao.NOTICE_DETAIL_CACHE_1, RedisDao.NOTICE_DETAIL_CACHE_2);
                redisDao.removeCache(RedisDao.NOTICE_DETAIL_CACHE_1);
                break;
            case 5:
                redisDao.unionCache(RedisDao.NEWS_CACHE_2, RedisDao.NEWS_CACHE_1, RedisDao.NEWS_CACHE_2);
                redisDao.removeCache(RedisDao.NEWS_CACHE_1);
                break;
            case 6:
                redisDao.unionCache(RedisDao.PATENT_CACHE_2, RedisDao.PATENT_CACHE_1, RedisDao.PATENT_CACHE_2);
                redisDao.removeCache(RedisDao.PATENT_CACHE_1);
                break;
            case 7:
                redisDao.unionCache(RedisDao.VARIETY_CACHE_2, RedisDao.VARIETY_CACHE_1, RedisDao.VARIETY_CACHE_2);
                redisDao.removeCache(RedisDao.VARIETY_CACHE_1);
                break;
            case 8:
                redisDao.unionCache(RedisDao.VR_CACHE_2, RedisDao.VR_CACHE_1, RedisDao.VR_CACHE_2);
                redisDao.removeCache(RedisDao.VR_CACHE_1);
                break;
            default:
                return;
        }
    }

    @Override
    public Message removeExpertCache(int state){
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除专家缓存消息","");
        String cacheName = RedisDao.EXPERT_CACHE_1;
        if(state == 2) cacheName = RedisDao.EXPERT_CACHE_2;
        return this.removeCache(cacheName,message);
    }
    @Override
    public Message removePatentCache(int state) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除专利缓存消息","");
        String cacheName = RedisDao.PATENT_CACHE_1;
        if(state == 2) cacheName = RedisDao.PATENT_CACHE_2;
        return this.removeCache(cacheName,message);
    }
    @Override
    public Message removeArticleCache(int state) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除论文缓存消息","");
        String cacheName = RedisDao.ARTICLE_CACHE_1;
        if(state == 2) cacheName = RedisDao.ARTICLE_CACHE_2;
        return this.removeCache(cacheName,message);
    }

    @Override
    public Message removeNoticeCache(int state) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除公告缓存消息","");
        String cacheName = RedisDao.NOTICE_CACHE_1;
        if(state == 2) cacheName = RedisDao.NOTICE_CACHE_2;
        return this.removeCache(cacheName,message);
    }

    @Override
    public Message removeNewsCache(int state) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除新闻缓存消息","");
        String cacheName = RedisDao.NEWS_CACHE_1;
        if(state == 2) cacheName = RedisDao.NEWS_CACHE_2;
        return this.removeCache(cacheName,message);
    }

    @Override
    public Message removeVarietyCache(int state) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除审定品种缓存消息","");
        String cacheName = RedisDao.VARIETY_CACHE_1;
        if(state == 2) cacheName = RedisDao.VARIETY_CACHE_2;
        return this.removeCache(cacheName,message);
    }


    @Override
    public PageBean<MyProxy> getCheckedProxy(int pc, int ps) throws Exception {
        String cacheName = RedisDao.PROXY_CHECKED_CACHE;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<MyProxy> pb = new PageBean<>();
        if(totalRecords == proxyCheckedList.size()){
            pb.setBeanList(RedisPageUtils.pageProxyList(proxyCheckedList,pc,ps));
        }else{
            //清空List，再进行添加
            proxyCheckedList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                proxyCheckedList.add(JSON.parseObject(iterator.next(), MyProxy.class));
            }
            pb.setBeanList(RedisPageUtils.pageProxyList(proxyCheckedList,pc,ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }

    @Override
    public PageBean<MyProxy> getProxy(int pc, int ps) throws Exception {
        String cacheName = RedisDao.PROXY_NO_CHECKED_CACHE;
        int totalRecords = redisDao.getResultCount(cacheName);
        PageBean<MyProxy> pb = new PageBean<>();
        if(totalRecords == proxyList.size()){
            pb.setBeanList(RedisPageUtils.pageProxyList(proxyList,pc,ps));
        }else{
            //清空List，再进行添加
            proxyList.clear();
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()) {
                proxyList.add(JSON.parseObject(iterator.next(), MyProxy.class));
            }
            pb.setBeanList(RedisPageUtils.pageProxyList(proxyList,pc,ps));
        }
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(totalRecords);
        return pb;
    }


    private Message removeCache(String cacheName,Message message){
        try {
            redisDao.removeCache(cacheName);
            message.setMessage_content("删除成功！");
        }catch (Exception e){
            logger.error(e.getMessage());
            message.setMessage_content("删除失败！");
        }
        return message;
    }
}
