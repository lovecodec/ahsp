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
public class Admin implements Serializable{

    private static final long serialVersionUID = 7367548617518407244L;
    private String admin_id;
    private String admin_username;
    private String admin_password;
    private String last_login_time;
    private String last_login_ip;
}
