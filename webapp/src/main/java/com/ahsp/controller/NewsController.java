package com.ahsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahsp.po.News;
import com.ahsp.po.PageBean;
import com.ahsp.service.NewsService;

@Controller
public class NewsController {
	@Autowired
	@Qualifier("newsService")
	private NewsService newsService;
	
	@RequestMapping("/loadNews")
	@ResponseBody
	public List<News> loadNews() throws Exception{
		return newsService.loadNews();
	}

	@RequestMapping("/loadAllNews")
	@ResponseBody
	public PageBean<News> loadAllNews(int pc) throws Exception{
		int ps = 10;
		PageBean<News> pb = newsService.loadAllNews(pc, ps);
		return pb;
	}

}
