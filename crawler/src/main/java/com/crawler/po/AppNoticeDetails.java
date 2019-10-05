package com.crawler.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author Dr.chen
 * @date 2019年04月24日
 * @Description:品种申请公告详情
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AppNoticeDetails implements Serializable{
	private static final long serialVersionUID = -3462212224421247316L;
	private String noticeDetailId;
	private String cropName;
	private String BreedName;
	private String appNo;
	private String appDate;
	private String applicant;
	private String type;
	private String state;
	private String pubDate;
	private String grantNo;
	private String grantDate;
	private String pubNo;
	private String owner;
	private String ownerAddress;
}
