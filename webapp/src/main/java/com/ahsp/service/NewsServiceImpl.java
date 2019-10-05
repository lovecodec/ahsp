package com.ahsp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ahsp.dao.RedisDao;
import com.ahsp.po.Message;
import com.ahsp.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahsp.mapper.NewsMapper;
import com.ahsp.po.News;
import com.ahsp.po.PageBean;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private RedisDao redisDao;
	private Logger logger = LoggerFactory.getLogger("newsLogger");
	
	@Override
	public List<News> loadNews() throws Exception {	
		return newsMapper.loadNews();
	}

	@Override
	public PageBean<News> loadAllNews(int pc, int ps) throws Exception {
		
		//service层完成pageBean的封装
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		PageBean<News> pb = new PageBean<News>();
		pb.setPc(pc);
		pb.setPs(ps);
		//去dao层查询记录数
		pb.setTr(newsMapper.findCount());
		pb.setBeanList(newsMapper.loadAllNews(upBound, lowerBound));
		return pb;
	}

	@Override
	public Message addAllNews(int state) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加新闻消息","");
		if(state!=2 && state != 1){
			message.setMessage_content("数据已经持久化到数据库，无需重复添加！");
			return message;
		}
		try {
			List<News> newsList = new ArrayList<>();
			String cacheName = RedisDao.NEWS_CACHE_1;
			if(state == 2) cacheName = RedisDao.NEWS_CACHE_2;
			//读取数据并且转换为List<Patent>
			Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
			while (iterator.hasNext()){
				String newsStr = iterator.next();
				newsList.add(JSON.parseObject(newsStr, News.class));
			}
			//将List添加到MySQL数据库中
			newsMapper.addAllNews(newsList);
			//清空缓存
			redisDao.removeCache(cacheName);
			//设置消息
			message.setMessage_content("添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setMessage_content("添加失败！");
		}
		return message;
	}

	@Override
	public Message deleteNewsById(String[] idArr) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除新闻消息","");
		try {
			newsMapper.deleteNewsById(idArr);
			message.setMessage_content("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setMessage_content("删除失败！");
		}
		return message;
	}
}
