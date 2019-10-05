package com.ahsp.service;

import com.ahsp.dao.RedisDao;
import com.ahsp.mapper.VrMapper;
import com.ahsp.po.AppNoticeDetails;
import com.ahsp.po.Message;
import com.ahsp.po.PageBean;
import com.ahsp.po.VarietyRight;
import com.ahsp.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("vrService")
public class VrServiceImpl implements VrService {

	@Autowired
	private VrMapper vrMapper;

	@Autowired
	private RedisDao redisDao;
	
	@Override
	public PageBean<VarietyRight> findAllVr(int pc, int ps) throws Exception {
		int upBound = (pc - 1) * ps;
		int lowerBound = ps;
		// 在service层完成PageBean的组装
		PageBean<VarietyRight> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		// 查询出一共有多少条记录
		pb.setTr(vrMapper.findAllVrCount());
		pb.setBeanList(vrMapper.findAllVr(upBound, lowerBound));
		return pb;
	}

	@Override
	public PageBean<VarietyRight> findVrByCriteria(VarietyRight criteria, int pc, int ps) throws Exception {
		int upBound = (pc - 1) * ps;
		int lowerBound = ps;
		// 在service层完成PageBean的组装
		PageBean<VarietyRight> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(vrMapper.findVrCountByCriteria(criteria));
		pb.setBeanList(vrMapper.findVrByCriteria(criteria, upBound, lowerBound));
		return pb;
	}

	@Override
	public AppNoticeDetails loadVrInfo(String vrname, String type) throws Exception {
		return vrMapper.loadVrInfo(vrname,type);
	}

	@Override
	public Message addVr(VarietyRight vr) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加品种权消息","");
		try {
			vrMapper.addVr(vr);
			message.setMessage_content("添加成功！");
		}catch (Exception e){
			message.setMessage_content("添加失败！");
		}
		return message;
	}

	@Override
	public Message deleteVr(String appCode, String grantCode) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除品种权消息","");
		try {
			vrMapper.deleteVr(appCode,grantCode);
			message.setMessage_content("删除成功！");
		}catch (Exception e){
			message.setMessage_content("删除失败！");
		}
		return message;
	}

	@Override
	public Message addAllVr(int state) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加品种权消息","");
		if(state!=2 && state != 1){
			message.setMessage_content("数据已经持久化到数据库，无需重复添加！");
			return message;
		}
		try {
			List<VarietyRight> vrList = new ArrayList<>();
			String cacheName = RedisDao.VR_CACHE_1;
			if(state == 2) cacheName = RedisDao.VR_CACHE_2;
			//读取数据并且转换为List<VarietyRight>
			Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
			while (iterator.hasNext()){
				String vrStr = iterator.next();
				vrList.add(JSON.parseObject(vrStr, VarietyRight.class));
			}
			//将List添加到MySQL数据库中
			vrMapper.addAllVr(vrList);
			//清空缓存
			redisDao.removeCache(cacheName);
			//设置消息
			message.setMessage_content("添加成功！");
		} catch (Exception e) {
			message.setMessage_content("添加失败！");
		}
		return message;
	}

	@Override
	public Message updateVr(VarietyRight vr) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"更新品种权消息","");
		try {
			vrMapper.updateVr(vr);
			message.setMessage_content("更新成功！");
		}catch (Exception e){
			message.setMessage_content("更新失败！");
		}
		return message;
	}
}
