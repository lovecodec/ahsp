package com.crawler.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 * @author Dr.chen
 * @date 2019年04月21日
 * @Description:农业新闻类
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class News implements Serializable{
	private static final long serialVersionUID = 1118728232782512630L;
	/*新闻id*/
	private String news_id;
	/*新闻标题*/
	private String news_title;
	/*新闻发布时间以及新闻发布人*/
	private String news_author;
	/*新闻摘要*/
	private String news_summary;
	/*新闻链接*/
	private String news_href;
	/*新闻发布时间*/
	private String news_time;

}
