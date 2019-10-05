package com.ahsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahsp.po.Notice;
import com.ahsp.po.PageBean;
import com.ahsp.service.NoticeService;

@Controller
public class NoticeController {
	@Autowired
	@Qualifier("noticeService")
	private NoticeService noticeService;
	
	
	@RequestMapping("/loadNotice")
	@ResponseBody
	public List<Notice> loadNotice() throws Exception{
		return noticeService.loadNotice();
	}
	
	@RequestMapping("/loadNoticeByType")
	@ResponseBody
	public PageBean<Notice> loadNoticeByType(String pc,String noticeType) throws Exception{
		int ps = 15;
		int currentPage = Integer.parseInt(pc);
		return noticeService.loadNoticeByType(currentPage, ps, noticeType);
	}

}
