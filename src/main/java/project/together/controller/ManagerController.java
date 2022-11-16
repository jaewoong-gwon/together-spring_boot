package project.together.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.together.service.ManagerService;
import project.together.vo.Manager;
import project.together.vo.Notice;
import project.together.vo.Organization;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/together")
public class ManagerController {

    @Autowired
    private final ManagerService managerService;

    @PostMapping("/manager/start")
    public ResponseEntity<Manager> singUpAndLogin(@RequestBody String accessToken) {
        log.info("ManagerSignUpAndLogin : {}", accessToken);
        JSONObject info = new JSONObject(accessToken);

        Manager manager = managerService.signUpAndLogin(info);
        if (manager == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(manager);
    }

    @GetMapping("/manager/create/notice")
    public ResponseEntity<Notice> createNotice(Notice notice) {
        log.info("createNotice : {}", notice);
        Notice resNotice = managerService.createNotice(notice);
        if (resNotice == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(resNotice);
    }


    @GetMapping("/manager/update/notice")
    public ResponseEntity<Notice> updateNotice(Notice notice) {
        log.info("updateNotice : {}", notice);
        Notice resNotice = managerService.updateNotice(notice);
        if (resNotice == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(resNotice);
    }

    @GetMapping("/manager/delete/notice")
    public ResponseEntity<String> deleteNotice(Notice notice) {
        log.info("deleteNotice : {}", notice);
        if (managerService.deleteNoticeById(notice) > 0) return ResponseEntity.status(HttpStatus.OK).body("삭제성공");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않음!");
    }

    @GetMapping("/manager/find/all/users")
    public ResponseEntity<?> findAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(managerService.findAllUsers());
    }

    @GetMapping("/manager/find/notice")
    public ResponseEntity<?> findNotice(Notice notice) {
        log.info("findNotice : {}", notice);
        return ResponseEntity.status(HttpStatus.OK).body(managerService.findNoticeById(notice));
    }

    @GetMapping("/manager/certificate/org")
    public ResponseEntity<?> certificateOrganization(Organization organization) {
        int result = managerService.certificateOrganization(organization);
        if (result > 0) return ResponseEntity.status(HttpStatus.OK).body("수정 완료!");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않음");
    }

}
