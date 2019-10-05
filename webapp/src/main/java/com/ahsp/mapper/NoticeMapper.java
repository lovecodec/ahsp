package com.ahsp.mapper;

import com.ahsp.po.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {

	List<Notice> loadNotice() throws Exception;
	// 获取记录数
	int findCountByType(@Param("noticeType") String noticeType) throws Exception;
	// 根据公告类型加载公告
	List<Notice> loadNoticeByType(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound,
                                         @Param("noticeType") String noticeType) throws Exception;

	//公告数据的CRUD
	List<Notice> getNotice(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;
	int findCount() throws Exception;
	void deleteNoticeById(String[] idArr) throws Exception;
	void addAllNotice(List<Notice> noticeList) throws Exception;
}
