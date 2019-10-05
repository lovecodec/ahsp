package com.ahsp.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SpiderStatus implements Serializable{
    private static final long serialVersionUID = 7395877028472718396L;
    private int spider_id;
    private String spider_name;
    private String lastStarttime;
    private int new_count;
    private int total_count;
}
