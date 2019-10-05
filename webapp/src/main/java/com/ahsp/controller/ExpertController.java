package com.ahsp.controller;

import com.ahsp.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ahsp.service.ExpertService;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExpertController {
	
	@Autowired
	@Qualifier("expertService")
	private ExpertService expertService;
	
	@RequestMapping("/loadAllExpert")
	public PageBean<ExpertExt> loadAllExpert(int pc) throws Exception{
		int ps = 10;
		PageBean<ExpertExt> pb = expertService.loadAllExpert(pc, ps);
		return pb;
	}
	
	@RequestMapping("/getPatentInfo")
	public PageBean<Patent> getPatentInfo(int pc,String name) throws Exception{
		int ps = 5;
		PageBean<Patent> pb = expertService.findPatentByEname(name, pc, ps);
		return pb;
	}
	
	@RequestMapping("/getArticleInfo")
	public PageBean<Article> getArticleInfo(int pc,String name) throws Exception{
		int ps = 5;
		PageBean<Article> pb = expertService.findArticleByEname(name, pc, ps);
		return pb;
	}
	
	@RequestMapping("/getExpertInfo")
	public ExpertExt getExpertInfo(String name) throws Exception{
		return expertService.getExpertInfo(name);
	}
}
