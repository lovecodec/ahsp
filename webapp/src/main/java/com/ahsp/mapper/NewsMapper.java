package com.ahsp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ahsp.po.News;

public interface NewsMapper {
	List<News> loadNews() throws Exception;
	
	//无条件时的记录数
	int findCount() throws Exception;
	
	List<News> loadAllNews(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;

	void addAllNews(List<News> newsList) throws Exception;
	void deleteNewsById(String[] idArr) throws Exception;
}
