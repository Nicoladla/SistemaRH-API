package com.tastecamp.api.model;

import com.tastecamp.api.dto.CandidateDTO;
import com.tastecamp.api.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Candidate {

    public Candidate(CandidateDTO data){
        this.nome= data.nome();
        this.status= Status.RECEBIDO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    private String nome;

    @Column(length = 11, nullable = false)
    private Status status;
}
