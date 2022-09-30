package project.together.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.together.dto.Servant;
import project.together.service.NaverLoginService;
import project.together.service.SessionManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Slf4j
@RestController
@AllArgsConstructor
public class snsLoginController {
    private NaverLoginService naverLoginService;
    private SessionManager sessionManager;

    @GetMapping("login/oauth2/code/naver")
    public ResponseEntity<?> login(@RequestParam String code, HttpServletResponse response){
        //프론트에서 요청, 위의 경로로 리다이렉트 되면 인증 code 발급. 해당 코드로 Access_Token 발급 요청.
        String access_Token = naverLoginService.getToken(code);

        Servant user = naverLoginService.getUserInfo(access_Token);
        Cookie cookie = sessionManager.createSession(user,response);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        if(cookie != null) return new ResponseEntity<>(headers, HttpStatus.OK);

        return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
    }

    //쿠키에 사용자 정보 담아서 세션 리턴.
    @GetMapping("api/login/check")
    public String check(@RequestParam String check){

//        Servant servant = naverLoginService.getServant();
//        log.info(servant.getSerName());
//        naverLoginService.setCheck(check.get("check"));
        return "";
    }
}
