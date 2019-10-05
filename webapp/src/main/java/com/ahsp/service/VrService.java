package com.ahsp.service;

import com.ahsp.po.*;

import java.util.List;

public interface VrService {
	
	PageBean<VarietyRight> findAllVr(int pc, int ps) throws Exception;
	
	PageBean<VarietyRight> findVrByCriteria(VarietyRight criteria, int pc, int ps) throws Exception;
	
	AppNoticeDetails loadVrInfo(String vrname, String type) throws Exception;

	Message addVr(VarietyRight vr);
	Message deleteVr(String appCode,String grantCode);
	Message addAllVr(int state);
	Message updateVr(VarietyRight vr);

}
