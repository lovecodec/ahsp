package com.ahsp.mapper;

import java.util.List;

import com.ahsp.po.Variety;
import org.apache.ibatis.annotations.Param;

import com.ahsp.po.AppNoticeDetails;
import com.ahsp.po.VarietyRight;

public interface VrMapper {

	List<VarietyRight> findAllVr(@Param("upBound") int pc, @Param("lowerBound") int ps) throws Exception;

	int findAllVrCount() throws Exception;

	//多条件组合查询的结果数量
	int findVrCountByCriteria(@Param("criteria") VarietyRight criteria) throws Exception;
	//多条件组合查询的结果
	List<VarietyRight> findVrByCriteria(@Param("criteria") VarietyRight criteria, @Param("upBound") int pc, @Param("lowerBound") int ps) throws Exception;

	AppNoticeDetails loadVrInfo(@Param("vrname") String vrname, @Param("type") String type) throws Exception;

	void addVr(VarietyRight vr) throws Exception;
	void deleteVr(@Param("appCode") String appCode,@Param("grantCode") String grantCode) throws Exception;
	void updateVr(VarietyRight vr) throws Exception;
	void addAllVr(List<VarietyRight>  vrList) throws Exception;

}
