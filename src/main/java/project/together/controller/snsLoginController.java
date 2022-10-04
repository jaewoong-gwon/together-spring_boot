package project.together.controller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.together.service.NaverLoginService;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class snsLoginController {
    private NaverLoginService naverLoginService;

    
    //Get 으로 바꿀껏. -> naver redirect시 get 으로 넘어옴
    @GetMapping("login/oauth2/code/naver")
    //content-type 설정해서 전송하기
    public ResponseEntity<?> login(@RequestParam Map<String,String> params) throws IOException {
        System.out.println(params);
        //access_token 받아와서 Naver 에 UserInfo 요청
        return naverLoginService.getUserInfo(params.get("access_token"));
        // getUserInfo()
        // 1. access_token 을 통한 사용자 정보 요청
        // 2. 받아온 사용자 정보를 통한 db 확인
        // 3. db 확인후 적절한 response return.

    }

}
