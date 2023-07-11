package com.tastecamp.api.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tastecamp.api.dto.CandidateDTO;

@RestController
@RequestMapping("/api/v1/hiring")
public class SystemController {
    @PostMapping("/start")
    public int introduceCandidate(@RequestBody CandidateDTO body){

        
        return 1;
    }
}
