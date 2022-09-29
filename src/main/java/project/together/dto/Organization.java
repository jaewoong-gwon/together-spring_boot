package project.together.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Organization {
    private String orgId;
    private String orgName;
    private String orgMobile;
    private int orgCertified; //기관 인증 여부
    private String orgEmail;
    private String orgType; //기관 유형
    private String orgAddress;
    private String orgSns;
    private String orgManName;
    private Date orgManBirth;
    private int orgManGender;
    private String ortManMobile;
}
