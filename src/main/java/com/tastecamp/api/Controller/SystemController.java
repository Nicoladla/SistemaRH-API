package com.tastecamp.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tastecamp.api.Exception.BadRequestException;
import com.tastecamp.api.Exception.ConflictException;
import com.tastecamp.api.Exception.NotFoundException;
import com.tastecamp.api.Service.SystemService;
import com.tastecamp.api.dto.CandidateDTO;
import com.tastecamp.api.dto.IdCandidateDTO;
import com.tastecamp.api.enums.StatusCandidate;
import com.tastecamp.api.model.Candidate;

@RestController
@RequestMapping("/api/v1/hiring")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class SystemController {

    @Autowired
    private SystemService service;

    @PostMapping("/start")
    public Long startProcess(@RequestBody CandidateDTO body) {
        try {
            Candidate candidate = service.PostCandidate(body);
            return candidate.getId();
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (ConflictException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/schedule")
    public void scheduleInterview(@RequestBody IdCandidateDTO body) {
        try {
            service.scheduleInterview(body);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/disqualify")
    public void disqualifyCandidate(@RequestBody IdCandidateDTO body) {
        try {
            service.deleteCandidate(body);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/approve")
    public void approveCandidate(@RequestBody IdCandidateDTO body) {
        try {
            service.approveCandidate(body);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/status/candidate/{id}")
    public StatusCandidate checkCandidateStatus(@PathVariable Long id) {
        try {
            return service.getStatusCandidateById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/approved")
    public List<String> getApprovedCandidates() {
        return service.getApprovedCandidates();
    }

    @GetMapping
    public List<Candidate> getCandidates() {
        return service.getCandidates();
    }
}
