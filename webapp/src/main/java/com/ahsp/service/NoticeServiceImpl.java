package com.ahsp.service;

import com.ahsp.dao.RedisDao;
import com.ahsp.mapper.NoticeMapper;
import com.ahsp.po.Message;
import com.ahsp.po.Notice;
import com.ahsp.po.PageBean;
import com.ahsp.po.Patent;
import com.ahsp.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeMapper noticeMapper;

	@Autowired
	private RedisDao redisDao;

	static Logger logger = LoggerFactory.getLogger("noticeLogger");

	@Override
	public List<Notice> loadNotice() throws Exception {
		return noticeMapper.loadNotice();
	}

	@Override
	public PageBean<Notice> loadNoticeByType(int pc, int ps, String noticeType) throws Exception {
		PageBean<Notice> pb = new PageBean<>();
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(noticeMapper.findCountByType(noticeType));
		pb.setBeanList(noticeMapper.loadNoticeByType(upBound, lowerBound, noticeType));
		return pb;
	}

	@Override
	public PageBean<Notice> getNotice(int pc, int ps) throws Exception {
		PageBean<Notice> pb = new PageBean<>();
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(noticeMapper.findCount());
		pb.setBeanList(noticeMapper.getNotice(upBound, lowerBound));
		return pb;
	}

	@Override
	public Message deleteNoticeById(String[] idArr) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除公告消息","");
		try {
			noticeMapper.deleteNoticeById(idArr);
			message.setMessage_content("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setMessage_content("删除失败！");
		}
		return message;
	}

	@Override
	public Message addAllNotice(int state) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加公告消息","");
		if(state!=2 && state != 1){
			message.setMessage_content("数据已经持久化到数据库，无需重复添加！");
			return message;
		}
		try {
			List<Notice> noticeList = new ArrayList<>();
			String cacheName = RedisDao.NOTICE_CACHE_1;
			if(state == 2) cacheName = RedisDao.NOTICE_CACHE_2;
			//读取数据并且转换为List<Notice>
			Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
			while (iterator.hasNext()){
				String noticeStr = iterator.next();
				noticeList.add(JSON.parseObject(noticeStr, Notice.class));
			}
			//将List添加到MySQL数据库中
			noticeMapper.addAllNotice(noticeList);
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
}
