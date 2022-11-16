package project.together.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class Organization {
    private final String role = "org";
    private String orgId;
    private String orgName;
    private String orgMobile; // 기관 전화번호
    private Integer orgCertified; //기관 인증 여부
    private String orgEmail;
    private String orgType; //기관 유형
    private String orgAddress;
    private String orgManName;
    private Date orgManBirth;
    private Integer orgManGender;
    private String orgManMobile;
    private String orgSns;

}
