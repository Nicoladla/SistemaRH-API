package com.tastecamp.api.Service;

import java.util.ArrayList;
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
import com.tastecamp.api.enums.StatusCandidate;
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

    public void scheduleInterview(IdCandidateDTO body) {
        repository.findById(body.codCandidato()).map(candidate -> {
            if (candidate.getStatus() != StatusCandidate.RECEBIDO) {
                throw new NotFoundException("Candidato não encontrado");
            }

            candidate.setStatus(StatusCandidate.QUALIFICADO);

            return repository.save(candidate);
        }).orElseThrow(() -> new NotFoundException("Candidato não encontrado"));
    }

    public void deleteCandidate(IdCandidateDTO body) {
        CheckIfCandidateExists(body.codCandidato());

        repository.deleteById(body.codCandidato());
    }

    public void approveCandidate(IdCandidateDTO body) {
        repository.findById(body.codCandidato()).map(candidate -> {
            if (candidate.getStatus() != StatusCandidate.QUALIFICADO) {
                throw new NotFoundException("Candidato não encontrado");
            }

            candidate.setStatus(StatusCandidate.APROVADO);

            return repository.save(candidate);
        }).orElseThrow(() -> new NotFoundException("Candidato não encontrado"));
    }

    public StatusCandidate getStatusCandidateById(Long id) {
        CheckIfCandidateExists(id);

        Optional<Candidate> candidate = repository.findById(id);

        return candidate.get().getStatus();
    }

    public List<String> getApprovedCandidates() {
        List<Candidate> candidates = getCandidates();
        List<String> approvedCandidates = new ArrayList<>();

        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i).getStatus() == StatusCandidate.APROVADO) {
                approvedCandidates.add(candidates.get(i).getNome());
            }
        }

        return approvedCandidates;
    }

    public List<Candidate> getCandidates() {
        return repository.findAll();
    }

    private void CheckIfCandidateExists(Long id) {
        Optional<Candidate> candidate = repository.findById(id);

        if (!candidate.isPresent()) {
            throw new NotFoundException("Candidato não encontrado");
        }
    }
}
