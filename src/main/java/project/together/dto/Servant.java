package project.together.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Servant {
    private String serId; //ser_id
    private String serName;
    private Date serBirth;
    private int serGender;
    private String serMobile;
    private int serCumCoefficient; //누적 봉사 횟수
    private Time serCumTime; // 누적 봉사 시간.
    private String serEmail;
    private String serSns;

}
