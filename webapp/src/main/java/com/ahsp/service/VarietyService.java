package com.ahsp.service;

import com.ahsp.po.Message;
import com.ahsp.po.PageBean;
import com.ahsp.po.Variety;

public interface VarietyService {
	PageBean<Variety> findAllVariety(int pc, int ps) throws Exception;
	
	PageBean<Variety> findVarietyByCriteria(int pc, int ps, Variety criteria) throws Exception;

	Message addVariety(Variety variety);

	Message addAllVariety(int state);

	Message deleteVarietyById(String[] idArr);

	Message updateVariety(Variety variety);
}
