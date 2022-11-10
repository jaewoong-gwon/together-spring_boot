package project.together.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.together.service.UserService;
import project.together.vo.Organization;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/together")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> params) {
        log.info("loginInfo : {}", params);
        JSONObject loginInfo = new JSONObject(params);
        log.info("json : {}", loginInfo);
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginInfo));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> params) {
        log.info("signUpInfo : {}", params);
        JSONObject signUpInfo = new JSONObject(params);
        Object res = userService.signUp(signUpInfo);
        if (res == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 회원입니다!");
        } else return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/signup/org")
    public ResponseEntity<?> signUpOrganization(Organization organization) {
        log.info("org : {}", organization);
        return ResponseEntity.status(HttpStatus.OK).body(userService.signUpOrganization(organization));
    }

}
