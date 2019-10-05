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
public class Message implements Serializable{
    private static final long serialVersionUID = -1527753379624807293L;
    private String message_type;
    private String message_time;
    private String message_content;
}
