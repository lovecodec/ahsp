package com.ahsp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ahsp.po.Variety;
import org.aspectj.weaver.ast.Var;

public interface VarietyMapper {
	List<Variety> findAllVariety(@Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;
	//一共有多少条记录
	int findAllVarietyCount() throws Exception;
	//多条件组合查询，查询出一共有多少条记录，方便分页
	int findVarietyCount(@Param("criteria") Variety criteria) throws Exception;
	//多条件组合查询，查询出所有的集合
	List<Variety> findVarietyByCriteria(@Param("criteria") Variety criteria, @Param("upBound") int upBound, @Param("lowerBound") int lowerBound) throws Exception;



	//根据judgementNo删除审定品种
	void deleteVarietyById(String[] idArr) throws Exception;
	//添加审定品种
	void addVariety(Variety variety) throws Exception;
	void addAllVariety(List<Variety> varietyList) throws Exception;
	void updateVariety(Variety variety) throws Exception;
}
