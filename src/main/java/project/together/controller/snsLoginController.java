package project.together.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@AllArgsConstructor
public class snsLoginController {
    private NaverLoginService naverLoginService;
    private SessionManager sessionManager;

    @GetMapping("login/oauth2/code/naver")
    public void login(@RequestParam String code, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
        //프론트에서 요청, 위의 경로로 리다이렉트 되면 인증 code 발급. 해당 코드로 Access_Token 발급 요청.
        String access_Token = naverLoginService.getToken(code);

        Servant user = naverLoginService.getUserInfo(access_Token);

        String redirect_uri="http://183.104.217.115:3000";
//        sessionManager.createSession(user,response);
        redirectAttributes.addAttribute("user",user);
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(redirect_uri);

    }

    @GetMapping("")
    public String check(@RequestParam String check){

//        Servant servant = naverLoginService.getServant();
//        log.info(servant.getSerName());
//        naverLoginService.setCheck(check.get("check"));
        return "";
    }
}
