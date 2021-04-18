package com.practice.ipldashboard.repository;

import com.practice.ipldashboard.model.MatchData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchDataRepository extends JpaRepository<MatchData,Long> {   
}