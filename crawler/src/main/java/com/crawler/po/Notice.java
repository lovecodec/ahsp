package com.crawler.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Notice implements Serializable{
	private static final long serialVersionUID = -6880866873605412344L;
	private String notice_id;
	private String notice_title;
	private String notice_href;
	private String notice_date;
	/*公告类型*/
	private String notice_type;
}
