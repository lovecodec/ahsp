package com.crawler.proxy;

import lombok.*;

import java.io.Serializable;

/**
 * @author：Dr.chen
 * @date：2019/8/26 15:59
 * @Description：
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyProxy implements Serializable{
    private static final long serialVersionUID = -4924279080420940095L;
    private String hostName;    //主机
    private Integer port;       //端口
    private Long delay;         //延迟
    private String checkTime;   //校验时间
}
