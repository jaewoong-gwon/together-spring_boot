package project.together.vo;

import lombok.Data;

import java.io.Serializable;


//관리자 테이블 -> 서비스 자체 관리자, 추후 db 에 직접접근하여 사용하는 방식이 아닌, 만들어 둔 서비스를 통해 db 를 관리.
@Data
public class Manager implements Serializable {
    private final String role = "man";
    private String manId;
    private String manName;
    private String manMobile;
    private String manEmail;
    private String manSns;
}
