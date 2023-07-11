package com.tastecamp.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tastecamp.api.Service.SystemService;
import com.tastecamp.api.dto.CandidateDTO;
import com.tastecamp.api.model.Candidate;

@RestController
@RequestMapping("/api/v1/hiring")
public class SystemController {

    @Autowired
    private SystemService service;

    @PostMapping("/start")
    public Long startProcess(@RequestBody CandidateDTO body) {
        try {
            Candidate candidate= service.PostCandidate(body);
            return candidate.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public List<Candidate> getCandidates() {
        return service.getCandidates();
    }
}
