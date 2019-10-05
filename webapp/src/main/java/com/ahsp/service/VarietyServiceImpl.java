package com.ahsp.service;

import com.ahsp.dao.RedisDao;
import com.ahsp.mapper.VarietyMapper;
import com.ahsp.po.Message;
import com.ahsp.po.PageBean;
import com.ahsp.po.Variety;
import com.ahsp.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("varietyService")
public class VarietyServiceImpl implements VarietyService {

	@Autowired
	private VarietyMapper varietyMapper;
	@Autowired
	private RedisDao redisDao;
	
	@Override
	public PageBean<Variety> findAllVariety(int pc,int ps) throws Exception{
		/*查询的起点和终点*/
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		//在service层完成pagebean的组装
		PageBean<Variety> pb = new PageBean<>();
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(varietyMapper.findAllVarietyCount());
		List<Variety> list = varietyMapper.findAllVariety(upBound,lowerBound);
		pb.setBeanList(list);
		return pb;
	}

	@Override
	public PageBean<Variety> findVarietyByCriteria(int pc, int ps, Variety criteria) throws Exception {
		/*查询的起点和终点*/
		int upBound = (pc-1) * ps;
		int lowerBound = ps;
		//在service层完成pagebean的组装
		PageBean<Variety> pb = new PageBean<>();
		//设置属性
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(varietyMapper.findVarietyCount(criteria));
		List<Variety> list = varietyMapper.findVarietyByCriteria(criteria, upBound, lowerBound);
		pb.setBeanList(list);
		return pb;
	}

	@Override
	public Message addVariety(Variety variety) {
		Message message =CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加审定品种消息","");
		try{
			variety.setVarietyId(CommonUtils.uuid());
            varietyMapper.addVariety(variety);
            message.setMessage_content("添加成功！");
        }catch (Exception e){
            message.setMessage_content("添加失败！请检查您的操作是否有误!");
        }
        return message;
	}

	@Override
	public Message addAllVariety(int state) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"添加审定品种消息","");
		if(state!=2 && state != 1){
			message.setMessage_content("数据已经持久化到数据库，无需重复添加！");
			return message;
		}
		try {
			List<Variety> varietyList = new ArrayList<>();
			String cacheName = RedisDao.VARIETY_CACHE_1;
			if(state == 2) cacheName = RedisDao.VARIETY_CACHE_2;
			//读取数据并且转换为List<Patent>
			Iterator<String> iterator = redisDao.getResultByCacheName(cacheName).iterator();
			while (iterator.hasNext()){
				String varietyStr = iterator.next();
				varietyList.add(JSON.parseObject(varietyStr, Variety.class));
			}
			//将List添加到MySQL数据库中
			varietyMapper.addAllVariety(varietyList);
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
	public Message deleteVarietyById(String[] idArr) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"删除审定品种消息","");
		try {
			varietyMapper.deleteVarietyById(idArr);
			message.setMessage_content("删除成功");
		}catch (Exception e){
			message.setMessage_content("删除失败！");
		}
		return message;
	}

	@Override
	public Message updateVariety(Variety variety) {
		Message message = CommonUtils.configMessage(CommonUtils.getCurrentTime(),"更新审定品种消息","");
		try {
			varietyMapper.updateVariety(variety);
			message.setMessage_content("更新成功！");
		}catch (Exception e){
			message.setMessage_content("更新失败！");
		}
		return message;
	}
}
