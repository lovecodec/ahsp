package com.ahsp.controller;

import com.ahsp.po.PageBean;
import com.ahsp.po.Variety;
import com.ahsp.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VarietyController {

	@Autowired
	@Qualifier("varietyService")
	private VarietyService varietyService;

	/**
	 * 第一次加载进来
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllVariety")
	public PageBean<Variety> findAllVariety(String pc) throws Exception {
		if(pc==null||pc.trim().isEmpty()) {
			int currentPage = 1;
			// 设置一行显示的数量，这里由后台设置
			int ps = 10;
			PageBean<Variety> pb = varietyService.findAllVariety(currentPage, ps);
			return pb;
		}else {
			//获取当前页
			int currentPage = Integer.parseInt(pc);
			int ps = 10;
			PageBean<Variety> pb = varietyService.findAllVariety(currentPage, ps);
			return pb;
		}
	}
	
	@RequestMapping("/findVariety")
	public PageBean<Variety> findVariety(Variety vr, String pc) throws Exception {
		int currentPage = Integer.parseInt(pc);
		int ps = 10;
		return varietyService.findVarietyByCriteria(currentPage, ps, vr);
	}

}
