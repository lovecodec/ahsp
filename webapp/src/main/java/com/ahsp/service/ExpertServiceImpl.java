package com.ahsp.service;

import com.ahsp.dao.RedisDao;
import com.ahsp.mapper.ExpertMapper;
import com.ahsp.po.*;
import com.ahsp.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("expertService")
public class ExpertServiceImpl implements ExpertService {
	@Autowired
	private ExpertMapper expertMapper;
	@Autowired
	private RedisDao redisDao;
	private Logger logger = LoggerFactory.getLogger("expertLogger");

	@Override
	public PageBean<ExpertExt> loadAllExpert(int pc, int ps) throws Exception {
		//service层完成pageBean的封装
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		PageBean<ExpertExt> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		//查询记录数
		pb.setTr(expertMapper.findAllCount());
		List<ExpertExt> list = expertMapper.loadAllExpert(upBound, lowerBound);
		for (ExpertExt expertExt : list) {
			expertExt.setArticleCount(expertMapper.findArticleCountByEname(expertExt.getName()));
			expertExt.setPatentCount(expertMapper.findPatentCountByEname(expertExt.getName()));
		}
		pb.setBeanList(list);
		return pb;
	}

	@Override
	public PageBean<Expert> getAllExpert(int pc, int ps) throws Exception {
		//service层完成pageBean的封装
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		PageBean<Expert> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		//查询记录数
		pb.setTr(expertMapper.findAllCount());
		pb.setBeanList(expertMapper.getAllExpert(upBound,lowerBound));
		return pb;
	}

	@Override
	public PageBean<Article> findArticleByEname(String expertName, int pc, int ps) throws Exception {
		//service层完成pageBean的封装
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		PageBean<Article> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(expertMapper.findArticleCountByEname(expertName));
		pb.setBeanList(expertMapper.findArticleByEname(expertName, upBound, lowerBound));
		return pb;
	}

	@Cacheable(cacheNames = "patentPageBean",key="#expertName")
	@Override
	public PageBean<Patent> findPatentByEname(String expertName, int pc, int ps) throws Exception {
		//service层完成pageBean的封装
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		PageBean<Patent> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(expertMapper.findPatentCountByEname(expertName));
		pb.setBeanList(expertMapper.findPatentByEname(expertName, upBound, lowerBound));
		return pb;
	}

	@Override
	@Cacheable(cacheNames = "expert",key="#name")
	public ExpertExt getExpertInfo(String name) throws Exception {
		ExpertExt expertExt = expertMapper.getExpertInfo(name);
		expertExt.setArticleCount(expertMapper.findArticleCountByEname(name));
		expertExt.setPatentCount(expertMapper.findPatentCountByEname(name));
		return expertExt;
	}

    @Override
	public Message addAllExpert(int state) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加专家消息","");
		if(state!=2 && state != 1){
			message.setMessage_content("数据已经持久化到数据库，无需重复添加！");
			return message;
		}
		try {
			List<Expert> expertList = new ArrayList<>();
			String cacheName = RedisDao.EXPERT_CACHE_1;
			if(state == 2) cacheName = RedisDao.EXPERT_CACHE_2;
			//读取数据并且转换为List<Expert>
			Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
			while (iterator.hasNext()){
				String expertStr = iterator.next();
				expertList.add(JSON.parseObject(expertStr, Expert.class));
			}
			//将List添加到MySQL数据库中
			expertMapper.addAllExpert(expertList);
			//清空缓存
			redisDao.removeCache(cacheName);
			//设置消息
			message.setMessage_content("添加成功！");
		} catch (Exception e) {
			message.setMessage_content("添加失败！");
			logger.error(e.getMessage());
		}
        return message;
    }

	@Override
	public Message addExpert(Expert expert){
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加专家消息","");
		try {
			expertMapper.addExpert(expert);
			message.setMessage_content("添加成功！");
		} catch (Exception e) {
			message.setMessage_content("添加失败！");
			logger.error(e.getMessage());
		}
		return message;
	}

    @Override
    public Message deleteExpertByIds(String[] idArr) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除专家消息","");
		try {
			expertMapper.deleteExpertById(idArr);
			message.setMessage_content("删除成功！");
		} catch (Exception e) {
			message.setMessage_content("删除失败！");
			logger.error(e.getMessage());
		}
		return message;
    }

	@Override
	public Message updateExpert(Expert expert) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"更新专家消息","");
		try {
			expertMapper.updateExpert(expert);
			message.setMessage_content("更新成功！");
		} catch (Exception e) {
			message.setMessage_content("更新失败！");
		    logger.error(e.getMessage());
		}
		return message;
	}

	@Override
	public PageBean<Expert> findExpertByName(String expertName) throws Exception {
		PageBean<Expert> pb = new PageBean<>();
		int tr = expertMapper.findExpertCountByName(expertName);
		pb.setTr(tr);
		pb.setPc(1);
		pb.setPs(tr);
		pb.setBeanList(expertMapper.findExpertByName(expertName));
		return pb;
	}


	@Override
	public PageBean<Article> getAllArticle(int pc, int ps) throws Exception {
		//service层完成pageBean的封装
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		PageBean<Article> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(expertMapper.findArticleCount());
		pb.setBeanList(expertMapper.getAllArticle(upBound,lowerBound));
		return pb;
	}

	@Override
	public Message addAllArticle(int state) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加专家消息","");
		if(state!=2 && state != 1){
			message.setMessage_content("数据已经持久化到数据库，无需重复添加！");
			return message;
		}
		try {
			List<Article> articleList = new ArrayList<>();
			String cacheName = RedisDao.ARTICLE_CACHE_1;
			if(state == 2) cacheName = RedisDao.ARTICLE_CACHE_2;
			//读取数据并且转换为List<Article>
			Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
			while (iterator.hasNext()){
				String expertStr = iterator.next();
				articleList.add(JSON.parseObject(expertStr, Article.class));
			}
			//将List添加到MySQL数据库中
			expertMapper.addAllArticle(articleList);
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
	public Message deleteArticleById(String[] idArr) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除论文消息","");
		try {
			expertMapper.deleteArticleById(idArr);
			message.setMessage_content("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setMessage_content("删除失败！");
		}
		return message;
	}

	@Override
	public Message addArticle(Article article) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加论文消息","");
		try {
			article.setInfo(article.getYear() + "-" + article.getAuthor() + "-" + article.getPubPlace());
			expertMapper.addArticle(article);
			message.setMessage_content("添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setMessage_content("添加失败！");
		}
		return message;
	}

	@Override
	public Message updateArticle(Article article) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"更新论文消息","");
		try {
			article.setInfo(article.getYear() + "-" + article.getAuthor() + "-" + article.getPubPlace());
			expertMapper.updateArticle(article);
			message.setMessage_content("更新成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setMessage_content("更新失败！");
		}
		return message;
	}

    @Override
    public Message addAllPatent(int state) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加专利消息","");
        if(state!=2 && state != 1){
            message.setMessage_content("数据已经持久化到数据库，无需重复添加！");
            return message;
        }
        try {
            List<Patent> patentList = new ArrayList<>();
            String cacheName = RedisDao.PATENT_CACHE_1;
            if(state == 2) cacheName = RedisDao.PATENT_CACHE_2;
            //读取数据并且转换为List<Patent>
            Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
            while (iterator.hasNext()){
                String patentStr = iterator.next();
                patentList.add(JSON.parseObject(patentStr, Patent.class));
            }
            //将List添加到MySQL数据库中
            expertMapper.addAllPatent(patentList);
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
    public Message updatePatent(Patent patent) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"更新专利消息！","");
        try {
            expertMapper.updatePatent(patent);
            message.setMessage_content("更新成功！");
        } catch (Exception e) {
            logger.error(e.getMessage());
            message.setMessage_content("更新失败！");
        }
        return message;
    }

    @Override
    public Message deletePatentById(String[] idArr) {
        Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除专利消息","");
        try {
            expertMapper.deletePatentById(idArr);
            message.setMessage_content("删除成功！");
        } catch (Exception e) {
            logger.error(e.getMessage());
            message.setMessage_content("删除失败！");
        }
        return message;
    }

    @Override
    public PageBean<Patent> getAllPatent(int pc, int ps) throws Exception {
        //service层完成pageBean的封装
        int upBound = (pc-1) * ps;
        int lowerBound = ps;
        PageBean<Patent> pb = new PageBean<>();
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(expertMapper.findPatentCount());
        pb.setBeanList(expertMapper.getAllPatent(upBound,lowerBound));
        return pb;
    }

}
