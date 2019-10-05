package com.ahsp.service;

import com.ahsp.po.*;

import java.util.List;


public interface ExpertService {

    //根据专家姓名查论文
    PageBean<Article> findArticleByEname(String expertName, int pc, int ps) throws Exception;

    //加载所有的专家附带论文信息和专利信息
    PageBean<ExpertExt> loadAllExpert(int pc, int ps) throws Exception;

    //根据专家姓名查专利
    PageBean<Patent> findPatentByEname(String expertName, int pc, int ps) throws Exception;

    ExpertExt getExpertInfo(String name) throws Exception;


    //将缓存中的数据全部添加专家数据
    Message addAllExpert(int state);
    //只加载专家信息
    PageBean<Expert> getAllExpert(int pc,int ps) throws Exception;
    //手动添加专家数据
    Message addExpert(Expert expert);
    //根据id删除专家数据
    Message deleteExpertByIds(String[] idArr);
    Message updateExpert(Expert expert);
    PageBean<Expert> findExpertByName(String expertName) throws Exception;


    PageBean<Article> getAllArticle(int pc,int ps) throws Exception;
    Message addAllArticle(int state);
    Message deleteArticleById(String[] idArr);
    Message addArticle(Article article);
    Message updateArticle(Article article);

    Message addAllPatent(int state);
    Message updatePatent(Patent patent);
    Message deletePatentById(String[] idArr);
    PageBean<Patent> getAllPatent(int pc,int ps) throws Exception;
}
