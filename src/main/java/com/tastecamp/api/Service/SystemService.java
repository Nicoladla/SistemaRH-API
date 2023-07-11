package com.tastecamp.api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.tastecamp.api.Repository.SystemRepository;
import com.tastecamp.api.dto.CandidateDTO;
import com.tastecamp.api.model.Candidate;

@Service
public class SystemService {

    @Autowired
    private SystemRepository repository;

    public void PostCandidate(@RequestBody CandidateDTO body) {
        // if (body.name() == null || body.name().isEmpty()) {
        //     throw new RequisicaoRuimException("Nome inválido.");
        // }
        
        repository.save(new Candidate(body));
    }

    public List<Candidate> getCandidates(){
        return repository.findAll();
    }
}
