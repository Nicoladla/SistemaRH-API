package com.tastecamp.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tastecamp.api.model.Candidate;

public interface SystemRepository extends JpaRepository<Candidate, Long>{
    
}
