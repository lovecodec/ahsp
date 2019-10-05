package com.ahsp.po;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 专家扩展类
 * @author Dr.chen
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExpertExt extends Expert {
	private static final long serialVersionUID = 2202385633442466557L;
	private int articleCount;  //论文数量
	private int patentCount;   //专利数量
}
