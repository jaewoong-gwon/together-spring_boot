package project.together.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    private int notId;
    private String notWriter;
    private Date notWriTime; //작성시간
    private String notTitle;
    private String notContent;
}
