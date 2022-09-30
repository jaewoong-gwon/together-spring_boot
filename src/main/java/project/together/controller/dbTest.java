package project.together.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.together.dto.Comment;
import project.together.service.testService;

import java.util.List;

@RestController
@AllArgsConstructor
public class dbTest {
    private testService testService;

    @GetMapping("test")
    public ResponseEntity<?> test(){
        List<Comment> test = testService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(test);
    }
}
