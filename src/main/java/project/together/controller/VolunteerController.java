package project.together.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.together.service.VolunteerService;
import project.together.vo.Application;
import project.together.vo.Organization;
import project.together.vo.Servant;
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

    @GetMapping("find/org/volunteers")
    public ResponseEntity<?> findOrgVolunteers(Organization organization) {
        log.info("findOrgVolunteers : {}", organization);
        return ResponseEntity.status(HttpStatus.OK).body(volunteerService.findVolunteerAddServant(organization));
    }


    @GetMapping("find/all/volunteer")
    public ResponseEntity<?> findAllVolunteer(Volunteer volunteer) {
        log.info("findAllVolunteer : {}", volunteer);
        return ResponseEntity.status(HttpStatus.OK).body(volunteerService.findAllVolunteer(volunteer));
    }

    @GetMapping("find/my/volunteers")
    public ResponseEntity<?> findAllMyVolunteers(Servant servant) {
        if (servant.getSerId() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않습니다!");
        else
            return ResponseEntity.status(HttpStatus.OK).body(volunteerService.findAllApplicatedVolunteerByServantId(servant));
    }

    @GetMapping("find/volunteer")
    public ResponseEntity<?> findVolunteer(Volunteer volunteer) {
        log.info("findVolunteer : {}", volunteer);
        if (volunteer.getVolId() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않습니다!");

        Volunteer resVolunteer = volunteerService.findVolunteerById(volunteer);
        return ResponseEntity.status(HttpStatus.OK).body(resVolunteer);
    }

    @GetMapping("delete/volunteer")
    public ResponseEntity<String> deleteVolunteer(Volunteer volunteer) {
        log.info("deleteVolunteer : {}", volunteer);

        if (volunteerService.deleteVolunteerById(volunteer) > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않습니다!");
    }

    /*
        Application Table 관련
     */
    @GetMapping("create/application")
    public ResponseEntity<?> createApplication(Application application) {
        log.info("createApplication : {}", application);

        if (volunteerService.createApplication(application) > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("신청 완료");
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않습니다!");
    }

    @GetMapping("delete/application")
    public ResponseEntity<?> deleteApplication(Application application) {
        log.info("deleteApplication : {}", application);

        if (volunteerService.deleteApplication(application) > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id 값이 유효하지 않습니다!");
    }
}
