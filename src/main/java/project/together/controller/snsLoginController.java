package project.together.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.together.service.NaverLoginService;


@Slf4j
@RestController
@AllArgsConstructor
public class snsLoginController {
    private NaverLoginService naverLoginService;

    @GetMapping("login/oauth2/code/naver")
    public void login(@RequestParam String code){
        //프론트에서 요청, 위의 경로로 리다이렉트 되면 인증 code 발급. 해당 코드로 Access_Token 발급 요청.
        String access_Token = naverLoginService.getToken(code);
        String result = naverLoginService.getUserInfo(access_Token);

    }
}
