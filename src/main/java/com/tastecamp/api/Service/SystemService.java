package com.tastecamp.api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.tastecamp.api.Exception.ConflictException;
import com.tastecamp.api.Exception.BadRequestException;
import com.tastecamp.api.Repository.SystemRepository;
import com.tastecamp.api.dto.CandidateDTO;
import com.tastecamp.api.model.Candidate;

@Service
public class SystemService {

    @Autowired
    private SystemRepository repository;

    public Candidate PostCandidate(@RequestBody CandidateDTO body) {
        List<Candidate> candidates = getCandidates();

        if (body.nome() == null || body.nome().isEmpty()) {
            throw new BadRequestException("Nome inválido.");
        }

        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i).getNome().equals(body.nome())) {
                throw new ConflictException("Candidato já participa do processo.");
            }
        }

        Candidate candidate = repository.save(new Candidate(body));
        return candidate;
    }

    public List<Candidate> getCandidates() {
        return repository.findAll();
    }
}
