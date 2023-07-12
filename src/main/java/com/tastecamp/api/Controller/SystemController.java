package com.tastecamp.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tastecamp.api.Service.SystemService;
import com.tastecamp.api.dto.CandidateDTO;
import com.tastecamp.api.dto.IdCandidateDTO;
import com.tastecamp.api.enums.StatusCandidate;
import com.tastecamp.api.model.Candidate;

@RestController
@RequestMapping("/api/v1/hiring")
public class SystemController {

    @Autowired
    private SystemService service;

    @PostMapping("/start")
    public Long startProcess(@RequestBody CandidateDTO body) {
        try {
            Candidate candidate = service.PostCandidate(body);
            return candidate.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @PostMapping("/schedule")
    public void scheduleInterview(@RequestBody IdCandidateDTO body) {
        try {
            service.scheduleInterview(body);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/disqualify")
    public void disqualifyCandidate(@RequestBody IdCandidateDTO body) {
        try {
            service.deleteCandidate(body);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/approve")
    public void approveCandidate(@RequestBody IdCandidateDTO body) {
        try {
            service.approveCandidate(body);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/status/candidate/{id}")
    public StatusCandidate checkCandidateStatus(@PathVariable Long id) {
        try {
            return service.getStatusCandidateById(id);
        } catch (Exception e) {
            throw e;
        }
    }
    
    @GetMapping("/approved")
    public List<String> getApprovedCandidates() {
        try {
            return service.getApprovedCandidates();
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping
    public List<Candidate> getCandidates() {
        return service.getCandidates();
    }
}
