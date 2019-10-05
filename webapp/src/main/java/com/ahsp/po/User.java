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
public class User implements Serializable{
    private static final long serialVersionUID = 8172748334797861241L;
    private String user_id;
    private String username;
    private String password;
    private String last_login_time;
    private String last_login_ip;
    private int user_status;

}
