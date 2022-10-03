package project.together.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.together.dto.Servant;
import project.together.service.NaverLoginService;
import project.together.service.SessionManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class snsLoginController {
    private NaverLoginService naverLoginService;
    private SessionManager sessionManager;

    @PostMapping("/login/oauth2/access_token")
    //content-type 설정해서 전송하기
    public ResponseEntity<?>getUser(@RequestBody Map<String,String> access_token){
        System.out.println(access_token);
        JSONObject data = new JSONObject(access_token);
        System.out.println(data);
        return ResponseEntity.status(HttpStatus.OK).body("User");
    }
    @PostMapping("login/oauth2/code/naver")
    public ResponseEntity<?> login(@RequestBody Map<String,Object> user, HttpServletResponse response) throws IOException {
        JSONObject userInfo = new JSONObject(user);
        naverLoginService.process(userInfo);

        return ResponseEntity.status(HttpStatus.OK).body("User");
    }

}
