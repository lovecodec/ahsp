package com.ahsp.service;

import java.util.List;

import com.ahsp.po.Message;
import com.ahsp.po.Notice;
import com.ahsp.po.PageBean;

public interface NoticeService {
	List<Notice> loadNotice() throws Exception;
	
	PageBean<Notice> loadNoticeByType(int pc, int ps, String noticeType) throws Exception;

	PageBean<Notice> getNotice(int pc,int ps) throws Exception;

	Message deleteNoticeById(String[] idArr);

	Message addAllNotice(int state);
}
