package com.crawler.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Dr.chen
 * @date 2019年05月07日
 * @Description:专利类，23个关键字
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Patent implements Serializable{
	private static final long serialVersionUID = -9168428627964912240L;
	private String patentId;

	//申请号   --> AN
	private String sqh;
	//申请日  --> AD
	private String sqr;
	//申请省市 -->  CO
	private String sqss;
	//公开日  --> PD
	private String gkr;
	//公开号 --> PN
	private String gkh;
	//授权公告号  --> GN
	private String sqggh;
	//授权公告日  --> GD
	private String sqggr;
	//主分类号  --> MC
	private String zflh;
	//分类号  --> IC
	private String flh;
	//第一发明人 --> FI
	private String dyfmr;
	//申请人  	--> PA
	private String sqren;
	//申请人地址  --> DZ
	private String dz;
	//发明人    --> IN
	private String fmr;
	//当前权利人  --> PE
	private String dqqlr;
	//代理人   --> AT
	private String dlr;
	//代理机构  --> AG
	private String dljg;
	//优先权  --> PR
	private String yxq;
	//范畴分类  --> CT
	private String fcfl;
	//关键词  --> KW
	private String gjc;
	//状态  LG==3审中  LG==1有效 LG==2失效
	private String zt;
	//摘要  -->  AB
	private String zy;
	//主权力要求  --> CL
	private String zqlyq;
	//专利名称
	private String zlmc;

}
