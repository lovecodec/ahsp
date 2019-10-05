package com.ahsp.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Dr.chen
 * @date 2019年03月31日
 * @Description:
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Article implements Serializable {
	private static final long serialVersionUID = 7439697025847255405L;
	private String aid;    //文章id，唯一标识
	private String title;  //文章标题
	private String info;   //文章信息
	private String author; //文章作者
	private String year;   //刊登日期
	private String pubPlace;//刊登的地方
	private String url;    //文章链接
	private String citation;  //被引次数
}

