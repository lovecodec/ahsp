package com.crawler.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Expert implements Serializable {
    private static final long serialVersionUID = -8608728046017501063L;
    /*唯一主键*/
    private String eid;
    /*专家名称*/
    private String name;
    /*工作单位*/
    private String workplace;
    /*研究领域*/
    private String domain;
    /*专家信息采集来源*/
    private String gather;
    /*一个专家拥有多个论文*/
    private List<Article> articleList;

}
