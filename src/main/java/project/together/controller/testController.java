package project.together.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.together.service.testService;
import project.together.vo.Organization;
import project.together.vo.Servant;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/together")
@AllArgsConstructor
public class testController {

    private final testService testService;

    @GetMapping("/test/find/servant")
    public ResponseEntity<Servant> findUser(Servant user) {
        log.info("test-Servant : {}", user);
        Servant test = testService.findServantById(user.getSerId());
        return ResponseEntity.status(HttpStatus.OK).body(test);
    }

    @GetMapping("/test/find/all/servant")
    public ResponseEntity<List<Servant>> findAllServant() {
        List<Servant> servants = testService.testFindAllServant();
        return ResponseEntity.status(HttpStatus.OK).body(servants);
    }

    @GetMapping("/test/create/servant")
    public ResponseEntity<?> createServant(Servant user) {
        if (testService.createServant(user) > 0)
            return ResponseEntity.status(HttpStatus.OK).body(testService.findServantById(user.getSerId()));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
    }

    @GetMapping("/test/delete/servant")
    public ResponseEntity<?> deleteServant(Servant user) {
        if (testService.deleteServantById(user.getSerId()) > 0)
            return ResponseEntity.status(HttpStatus.OK).body("success");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
    }

    /*
            기관 매퍼 테스트
     */

    @GetMapping("/test/find/organization")
    public ResponseEntity<Organization> findOrganization(Organization user) {
        log.info("test-Organization : {}", user);
        Organization test = testService.findOrganizationById(user.getOrgId());
        return ResponseEntity.status(HttpStatus.OK).body(test);
    }

    @GetMapping("/test/find/all/organization")
    public ResponseEntity<List<Organization>> findAllOrganization() {
        List<Organization> organizations = testService.FindAllOrganization();
        return ResponseEntity.status(HttpStatus.OK).body(organizations);
    }

    @GetMapping("/test/create/organization")
    public ResponseEntity<?> createOrganization(Organization user) {
        if (testService.createOrganization(user) > 0)
            return ResponseEntity.status(HttpStatus.OK).body(testService.findOrganizationById(user.getOrgId()));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
    }

    @GetMapping("/test/delete/organization")
    public ResponseEntity<?> deleteOrganization(Organization user) {
        if (testService.deleteOrganizationById(user.getOrgId()) > 0)
            return ResponseEntity.status(HttpStatus.OK).body("success");

        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
    }

}
