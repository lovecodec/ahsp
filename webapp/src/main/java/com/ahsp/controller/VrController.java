package com.ahsp.controller;

import com.ahsp.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahsp.po.AppNoticeDetails;
import com.ahsp.po.PageBean;
import com.ahsp.po.VarietyRight;
import com.ahsp.service.VrService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VrController {
	@Autowired
	@Qualifier("vrService")
	private VrService vrService;
	
	@RequestMapping("/findAllVr")
	public PageBean<VarietyRight> findAllVr(int pc) throws Exception{
		int ps = 10;
		PageBean<VarietyRight> pb = vrService.findAllVr(pc, ps);
		return pb;
	}
	
	@RequestMapping("/findVrByCriteria")
	public PageBean<VarietyRight> findVrByCriteria(VarietyRight criteria,int pc) throws Exception{
		//每行要显示的数
		int ps = 10;
		PageBean<VarietyRight> pb = vrService.findVrByCriteria(criteria, pc, ps);
		return pb;
	}
	
	@RequestMapping("/loadVrInfo")
	public AppNoticeDetails loadVrInfo(String vrname,String type) throws Exception{
		return vrService.loadVrInfo(vrname, type);
	}

	@RequestMapping("/addVr")
	public Message addVr(VarietyRight vr) throws Exception{
		return vrService.addVr(vr);
	}

	@RequestMapping("/deleteVr")
	public Message deleteVr(String appCode,String grantCode) throws Exception{
		return vrService.deleteVr(appCode,grantCode);
	}

}
