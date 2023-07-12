package com.tastecamp.api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tastecamp.api.Exception.ConflictException;
import com.tastecamp.api.Exception.NotFoundException;
import com.tastecamp.api.Exception.BadRequestException;
import com.tastecamp.api.Repository.SystemRepository;
import com.tastecamp.api.dto.CandidateDTO;
import com.tastecamp.api.dto.IdCandidateDTO;
import com.tastecamp.api.enums.Status;
import com.tastecamp.api.model.Candidate;

@Service
public class SystemService {

    @Autowired
    private SystemRepository repository;

    public Candidate PostCandidate(CandidateDTO body) {
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

    public void updateCandidateStatus(IdCandidateDTO body) {
        repository.findById(body.codCandidato()).map(candidate -> {
            if (candidate.getStatus() != Status.RECEBIDO) {
                throw new NotFoundException("Candidato não encontrado");
            }

            candidate.setStatus(Status.QUALIFICADO);

            return repository.save(candidate);
        }).orElseThrow(() -> new NotFoundException("Candidato não encontrado"));
    }

    public List<Candidate> getCandidates() {
        return repository.findAll();
    }
}
