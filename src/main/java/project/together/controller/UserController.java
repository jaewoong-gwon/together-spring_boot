package project.together.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.together.service.UserService;
import project.together.vo.Notice;
import project.together.vo.Organization;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/together")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> params) {
        log.info("UserLoginInfo : {}", params);
        JSONObject loginInfo = new JSONObject(params);
        log.info("json : {}", loginInfo);
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginInfo));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> params) {
        log.info("UserSignUpInfo : {}", params);
        JSONObject signUpInfo = new JSONObject(params);
        Object res = userService.signUp(signUpInfo);
        if (res == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 회원입니다!");
        } else return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/signup/org")
    public ResponseEntity<?> signUpOrganization(@RequestBody Organization organization) {
        log.info("SignUpOrg : {}", organization);
        return ResponseEntity.status(HttpStatus.OK).body(userService.signUpOrganization(organization));
    }

    @GetMapping("/find/all/notice")
    public ResponseEntity<List<Notice>> findAllNotice() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllNotice());
    }

    @GetMapping("/find/notice")
    public ResponseEntity<Notice> findNoticeById(Notice notice) {
        log.info("findNoticeById : {}", notice);
        Notice resNotice = userService.findNoticeById(notice);
        if (resNotice == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(resNotice);
    }

}
