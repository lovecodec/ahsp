package com.crawler.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Dr.chen
 * @date 2019年03月31日
 * @Description:123
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Article implements Serializable{
	private static final long serialVersionUID = 480111228977191286L;
	private String aid;    //文章id，唯一标识
	private String title;  //文章标题
	private String info;   //文章信息
	private String author; //文章作者
	private String year;   //刊登日期
	private String pubPlace;//刊登的地方
	private String url;    //文章链接
	private String citation;	//被引数


}
