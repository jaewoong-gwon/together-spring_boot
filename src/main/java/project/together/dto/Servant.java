package project.together.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Servant {
    private String serId;
    private String serName;
    private Date serBirth;
    private int serGender;
    private String serMobile;
    private int SerCumCoefficient; //누적 봉사 횟수
    private int serCumTime; // 누적 봉사 시간.
    private String serSns;

}
