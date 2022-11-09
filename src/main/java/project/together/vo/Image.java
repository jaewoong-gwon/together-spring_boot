package project.together.vo;

import lombok.Data;


//이미지 테이블 -> 파일 이름을 id(PK)로 정의, 이름에 대한 상세 경로를  URL 에 저장.
@Data
public class Image {
    private String imgOrgId;
    private String imgUrl;
}
