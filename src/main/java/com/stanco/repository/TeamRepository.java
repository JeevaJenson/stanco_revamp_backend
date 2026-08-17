package com.stanco.repository;

import com.stanco.entity.Team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository
        extends JpaRepository<Team, Long> {


    Optional<Team> findByNameIgnoreCase(
            String name
    );


    List<Team> findByStatus(
            Integer status
    );


    boolean existsByNameIgnoreCase(
            String name
    );


    List<Team> findAllByOrderByNameAsc();
}