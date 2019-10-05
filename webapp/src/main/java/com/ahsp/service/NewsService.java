package com.ahsp.service;

import java.util.List;

import com.ahsp.po.Message;
import com.ahsp.po.News;
import com.ahsp.po.PageBean;

public interface NewsService {
	List<News> loadNews() throws Exception;
	PageBean<News> loadAllNews(int pc, int ps) throws Exception;
	Message addAllNews(int state);
	Message deleteNewsById(String[] idArr);
}
