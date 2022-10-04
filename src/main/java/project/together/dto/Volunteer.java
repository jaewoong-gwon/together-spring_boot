package project.together.dto;

import lombok.Data;

import java.util.Date;

//봉사 - 봉사 관련 정보 -> 게시판에 들어갈 정보
@Data
public class Volunteer {
    private int volId;
    private int volRecPersonnel; //모집 인원
    private Date volRecPeriod; //모집 기간
    private String volType;
    private String volOrganization;
    private int volCurNumber; //현재 모집 인원
    private String volLocation;
    private Date volStTime; //봉사 시작 시간
    private Date volEndTime; //봉사 종료 시간
    private String volTitle; //제목
    private String volContent; // 본문

}
