package project.together.vo;


import lombok.Data;

import java.sql.Date;
import java.sql.Time;

// db 조회 결과를 자바 클래스로 매핑 시 기본 생성자가 있어야함.
@Data
public class Servant {
    private final String role = "ser";
    private String serId;
    private String serName;
    private Date serBirth;
    private Integer serGender;
    private String serMobile;
    private String serEmail;
    private Integer serCumCoefficient; //누적 봉사 횟수
    private Time serCumTime; // 누적 봉사 시간.
    private String serSns;


}
