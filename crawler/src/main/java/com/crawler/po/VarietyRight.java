package com.crawler.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Dr.chen
 * @date 2019年01月11日
 * @Description:品种权
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VarietyRight implements Serializable {
    private static final long serialVersionUID = 4952735428404602238L;
    private String vrId;
    private String appCode;
    private String grantCode; //授权号
    private String vrType;//作物种类
    private String vrName;//品种名称
    private String appDate;//授权日
    private String noticeType;
    private String owner;//品种权人
    private String pubNo; //公告号

}
