package com.ahsp.mapper;

import com.ahsp.po.Article;
import com.ahsp.po.Expert;
import com.ahsp.po.ExpertExt;
import com.ahsp.po.Patent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpertMapper {

    //无条件查询时所有的专家记录数
    int findAllCount() throws Exception;

    //无条件查询时的专家数
    List<ExpertExt> loadAllExpert(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;

    //根据姓名查询专家的专利数
    int findPatentCountByEname(@Param("ename") String ename) throws Exception;

    //根据姓名查询专家的论文数
    int findArticleCountByEname(@Param("ename") String ename) throws Exception;

    //根据姓名查询专家的论文列表
    List<Article> findArticleByEname(@Param("ename") String expertName, @Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;

    //根据姓名查询专家的专利列表
    List<Patent> findPatentByEname(@Param("ename") String expertName, @Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;

    ExpertExt getExpertInfo(@Param("ename") String name) throws Exception;



    //用于后台管理的操作

    //论文数据的CRUD
    int findArticleCount() throws Exception;
    List<Article> getAllArticle(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;
    void addAllArticle(List<Article> articleList) throws Exception;
    void deleteArticleById(String[] idArr) throws Exception;
    void addArticle(Article article) throws Exception;
    void updateArticle(Article article) throws Exception;


    //专家数据的CRUD
    List<Expert> getAllExpert(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;
    void addAllExpert(List<Expert> expertList) throws Exception;
    void addExpert(Expert expert) throws Exception;
    void deleteExpertById(String[] idArr) throws Exception;
    void updateExpert(Expert expert) throws Exception;
    List<Expert> findExpertByName(@Param("expertName")String expertName) throws Exception;
    int findExpertCountByName(@Param("expertName") String expertName) throws Exception;

    //专利数据的CRUD
    List<Patent> getAllPatent(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;
    int findPatentCount() throws Exception;
    void addAllPatent(List<Patent> patentList) throws Exception;
    void updatePatent(Patent patent) throws Exception;
    void deletePatentById(String[] idArr) throws Exception;
    void addPatent(Patent patent) throws Exception;
}
