package com.practice.ipldashboard.repository;


import com.practice.ipldashboard.model.Team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    Team findByTeamName(String teamName);
}