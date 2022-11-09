package project.together.vo;

import lombok.Data;

import java.util.Date;


//공지사항 테이블
@Data
public class Notice {
    private int notId;
    private String notWriter;
    private Date notWriTime; //작성시간
    private String notTitle;
    private String notContent;
}
