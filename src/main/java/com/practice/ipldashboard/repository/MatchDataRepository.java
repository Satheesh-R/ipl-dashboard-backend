package com.practice.ipldashboard.repository;

import java.util.List;

import com.practice.ipldashboard.model.MatchData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDataRepository extends JpaRepository<MatchData,Long> {   
    @Query(value = "select distinct m.team1,count(m) from MatchData m group by m.team1")
    List<Object[]> findDistinctTeam1();
    @Query(value = "select distinct m.team2,count(m) from MatchData m group by m.team2")
    List<Object[]> findDistinctTeam2();
    @Query(value="select distinct m.matchWinner,count(m) from MatchData m group by m.matchWinner")
    List<Object[]> findTeamWins();
}