package com.ahsp.controller;

import com.ahsp.po.*;
import com.ahsp.service.*;
import com.ahsp.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：Dr.chen
 * @date：2019/8/20 21:34
 * @Description：
 */
@RestController
public class ResultController {
    @Autowired
    private ResultService resultService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private VarietyService varietyService;

    @Autowired
    private VrService vrService;

    @RequestMapping("/getExpertByCrawler")
    public PageBean<Expert> getExpertByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        if (state != 3)
            return resultService.getExpertByCrawler(pc, ps, state);
        return expertService.getAllExpert(pc, ps);
    }

    @RequestMapping("/getArticleByCrawler")
    public PageBean<Article> getArticleByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        if (state != 3)
            return resultService.getArticleByCrawler(pc, ps, state);
        return expertService.getAllArticle(pc, ps);
    }

    @RequestMapping("/getNoticeByCrawler")
    public PageBean<Notice> getNoticeByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        if (state != 3)
            return resultService.getNoticeByCrawler(pc, ps, state);
        return noticeService.getNotice(pc, ps);
    }

    @RequestMapping("/getNewsByCrawler")
    public PageBean<News> getNewsByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        if (state != 3)
            return resultService.getNewsByCrawler(pc, ps, state);
        return newsService.loadAllNews(pc, ps);
    }

    @RequestMapping("/getPatentByCrawler")
    public PageBean<Patent> getPatentByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        if (state != 3)
            return resultService.getPatentByCrawler(pc, ps, state);
        return expertService.getAllPatent(pc, ps);
    }

    @RequestMapping("/getNoticeDetailByCrawler")
    public PageBean<AppNoticeDetails> getNoticeDetailByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        return resultService.getNoticeDetailByCrawler(pc, ps, state);
    }

    @RequestMapping("/getVarietyByCrawler")
    public PageBean<Variety> getVarietyByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        if (state != 3)
            return resultService.getVarietyByCrawler(pc, ps, state);
        return varietyService.findAllVariety(pc, ps);
    }

    @RequestMapping("/getVrByCrawler")
    public PageBean<VarietyRight> getVrByCrawler(int pc, int state) throws Exception {
        int ps = 7;
        if (state != 3)
            return resultService.getVrByCrawler(pc, ps, state);
        return vrService.findAllVr(pc, ps);
    }

    //获取已经校验过的代理
    @RequestMapping("/getCheckedProxy")
    public PageBean<MyProxy> getCheckedProxy(int pc) throws Exception {
        int ps = 5;
        return resultService.getCheckedProxy(pc, ps);
    }

    //获取未校验过的代理
    @RequestMapping("/getProxy")
    public PageBean<MyProxy> getProxy(int pc) throws Exception {
        int ps = 5;
        return resultService.getProxy(pc, ps);
    }


    //专家CRUD接口
    @RequestMapping("/addAllExpert")
    public Message addAllExpert(int state) {
        return expertService.addAllExpert(state);
    }

    @RequestMapping("/addExpert")
    public Message addExpert(Expert expert) {
        expert.setEid(CommonUtils.uuid());
        expert.setGather("手动添加");
        return expertService.addExpert(expert);
    }

    @RequestMapping("/deleteExpertById")
    public Message deleteExpertById(String idString) {
        String[] ids = idString.split(",");
        return expertService.deleteExpertByIds(ids);
    }

    @RequestMapping("/updateExpert")
    public Message updateExpert(Expert expert) {
        return expertService.updateExpert(expert);
    }

    //删除所有专家信息，仅限清除缓存
    @RequestMapping("/deleteAllExpert")
    public Message deleteAllExpert(int state) {
        if (state == 1 || state == 2)
            return resultService.removeExpertCache(state);
        return CommonUtils.configMessage(CommonUtils.getCurrentTime(), "错误消息！", "不允许全部删除数据库中的专家数据！");
    }

    @RequestMapping("/findExpertByName")
    public PageBean<Expert> findExpertByName(String expertName) throws Exception {
        return expertService.findExpertByName(expertName);
    }


    //论文CRUD接口
    @RequestMapping("/addAllArticle")
    public Message addAllArticle(int state) {
        return expertService.addAllArticle(state);
    }

    @RequestMapping("/addArticle")
    public Message addArticle(Article article) {
        article.setAid(CommonUtils.uuid());
        return expertService.addArticle(article);
    }

    @RequestMapping("/updateArticle")
    public Message updateArticle(Article article) {
        return expertService.updateArticle(article);
    }

    @RequestMapping("/deleteArticleById")
    public Message deleteArticleById(String idString) {
        String[] ids = idString.split(",");
        return expertService.deleteArticleById(ids);
    }

    @RequestMapping("/deleteAllArticle")    //删除所有论文信息，仅限清除缓存
    public Message deleteAllArticle(int state) {
        if (state == 1 || state == 2)
            return resultService.removeArticleCache(state);
        return CommonUtils.configMessage(CommonUtils.getCurrentTime(), "错误消息！", "不允许全部删除数据库中的论文数据！");
    }

    //专利CRUD接口
    @RequestMapping("/addAllPatent")
    public Message addAllPatent(int state) {
        return expertService.addAllPatent(state);
    }

    @RequestMapping("/deletePatentById")
    public Message deletePatentById(String idString) {
        String[] ids = idString.split(",");
        return expertService.deletePatentById(ids);
    }

    @RequestMapping("/deleteAllPatent")
    public Message deleteAllPatent(int state) {
        if (state == 1 || state == 2)
            return resultService.removePatentCache(state);
        return CommonUtils.configMessage(CommonUtils.getCurrentTime(), "错误消息！", "不允许全部删除数据库中的专利数据！");
    }

    @RequestMapping("/updatePatent")
    public Message updatePatent(Patent patent) {
        return expertService.updatePatent(patent);
    }

    //公告CURD接口
    @RequestMapping("/addAllNotice")
    public Message addAllNotice(int state) {
        return noticeService.addAllNotice(state);
    }

    @RequestMapping("/deleteAllNotice")
    public Message deleteAllNotice(int state) {
        if (state == 1 || state == 2)
            return resultService.removeNoticeCache(state);
        return CommonUtils.configMessage(CommonUtils.getCurrentTime(), "错误消息！", "不允许全部删除数据库中的公告数据！");
    }

    @RequestMapping("/deleteNoticeById")
    public Message deleteNoticeById(String idString) {
        String[] ids = idString.split(",");
        return noticeService.deleteNoticeById(ids);
    }

    //新闻CRUD接口
    @RequestMapping("/addAllNews")
    public Message addAllNews(int state) {
        return newsService.addAllNews(state);
    }

    @RequestMapping("/deleteAllNews")
    public Message deleteAllNews(int state) {
        if (state == 1 || state == 2)
            return resultService.removeNewsCache(state);
        return CommonUtils.configMessage(CommonUtils.getCurrentTime(), "错误消息！", "不允许全部删除数据库中的新闻数据！");
    }

    @RequestMapping("/deleteNewsById")
    public Message deleteNewsById(String idString) {
        String[] ids = idString.split(",");
        return newsService.deleteNewsById(ids);
    }

    //审定品种CRUD接口
    @RequestMapping("/addAllVariety")
    public Message addAllVariety(int state) {
        return varietyService.addAllVariety(state);
    }

    @RequestMapping("/deleteAllVariety")
    public Message deleteAllVariety(int state) {
        if (state == 1 || state == 2)
            return resultService.removeVarietyCache(state);
        return CommonUtils.configMessage(CommonUtils.getCurrentTime(), "错误消息！", "不允许全部删除数据库中的审定品种数据！");
    }

    @RequestMapping("/addVariety")
    public Message addVariety(Variety variety) {
        return varietyService.addVariety(variety);
    }

    @RequestMapping("/deleteVarietyById")
    public Message deleteVarietyById(String idString) {
        String[] ids = idString.split(",");
        return varietyService.deleteVarietyById(ids);
    }

    @RequestMapping("/updateVariety")
    public Message updateVariety(Variety variety) {
        return varietyService.updateVariety(variety);
    }


    //品种权数据CRUD接口
    @RequestMapping("/addVr")
    public Message addVr(VarietyRight vr){
        return vrService.addVr(vr);
    }
    @RequestMapping("/addAllVr")
    public Message addAllVr(int state){
        return vrService.addAllVr(state);
    }
    @RequestMapping("/deleteVr")
    public Message deleteVr(String appCode,String grantCode){
        return vrService.deleteVr(appCode,grantCode);
    }
    @RequestMapping("/updateVr")
    public Message updateVr(VarietyRight vr){
        return vrService.updateVr(vr);
    }

}
