package project.together.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


//공지사항 테이블
@Data
public class Notice {
    private Integer notId;
    private String notWriter;
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm")
    private Date notWriTime; //작성시간
    private String notTitle;
    private String notContent;
}
