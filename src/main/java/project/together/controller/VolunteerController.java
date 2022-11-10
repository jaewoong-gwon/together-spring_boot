package project.together.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.together.service.VolunteerService;
import project.together.vo.Volunteer;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/together")
public class VolunteerController {
    private final VolunteerService volunteerService;

    @GetMapping("create/volunteer")
    public ResponseEntity<Volunteer> createVolunteer(Volunteer volunteer) {
        log.info("createVolunteer : {}", volunteer);

        Volunteer resVolunteer = volunteerService.createVolunteer(volunteer);

        /*
            db Insert 실패 -> 파라미터로 전달받아 매핑된 volunteer 변수에서
            db NOT NULL 로 선언된 칼럼 중 null 로 넘어온 필드가 있다는것 -> 해당 필드가 null 이라고
            error 전달.
         */
        if (resVolunteer == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(resVolunteer);
    }

    @GetMapping("find/all/volunteer")
    public ResponseEntity<?> findAllVolunteer() {
        return ResponseEntity.status(HttpStatus.OK).body(volunteerService.findAllVolunteer());
    }

    @GetMapping("find/volunteer")
    public ResponseEntity<?> findVolunteer(Volunteer volunteer) {
        log.info("findVolunteer : {}", volunteer);

        Volunteer resVolunteer = volunteerService.findVolunteerById(volunteer);

        if (resVolunteer == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(resVolunteer);
    }

    @GetMapping("update/vol/cur/number")
    public ResponseEntity<String> updateVolCurNumber(Volunteer volunteer) {
        log.info("updateVolCurNumber : {}", volunteer);

        if (volunteerService.updateVolCurNumberById(volunteer) > 0)
            return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패!");
    }

    @GetMapping("delete/volunteer")
    public ResponseEntity<String> deleteVolunteer(Volunteer volunteer) {
        log.info("deleteVolunteer : {}", volunteer);

        if (volunteerService.deleteVolunteerById(volunteer) > 0)
            return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않습니다!");
    }
}
