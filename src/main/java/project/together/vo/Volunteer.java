package project.together.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//봉사 - 봉사 관련 정보 -> 게시판에 들어갈 정보
@Data
public class Volunteer implements Serializable {
    private Integer volId;
    private String volOrganization; //모집 기관 id
    private Integer volRecPersonnel; //모집 인원
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm")
    private Date volRecPeriod; //마감 기간
    private String volType;
    private Integer volCurNumber; //현재 모집 인원
    private String volLocation;
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm")
    private Date volStTime; //봉사 시작 시간
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm")
    private Date volEndTime; //봉사 종료 시간
    private String volTitle; //제목
    private String volContent; // 본문

    /*
        봉사 테이블(N), 기관 테이블(1)
     */
    private String orgName;
    /*
        봉사 테이블(1), 신청 테이블(N)
     */
    private List<Application> appList;
}
