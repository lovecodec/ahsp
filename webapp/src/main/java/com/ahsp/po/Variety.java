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
public class Variety implements Serializable {
    private static final long serialVersionUID = -296257586437585486L;
    private String varietyId;
    private String judgementNo;
    private String vname;
    private String judgementYear;
    private String type;
    private String appCompany;
    private String judgeId;
    private String isTransgenosis;
    private String announcementID;

}

