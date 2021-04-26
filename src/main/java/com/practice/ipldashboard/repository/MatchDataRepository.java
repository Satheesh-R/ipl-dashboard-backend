package com.practice.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import com.practice.ipldashboard.model.MatchData;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDataRepository extends JpaRepository<MatchData, Long> {
    @Query(value = "select distinct m.team1,count(m) from MatchData m group by m.team1")
    List<Object[]> findDistinctTeam1();

    @Query(value = "select distinct m.team2,count(m) from MatchData m group by m.team2")
    List<Object[]> findDistinctTeam2();

    @Query(value = "select distinct m.matchWinner,count(m) from MatchData m group by m.matchWinner")
    List<Object[]> findTeamWins();

    List<MatchData> findByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    @Query(value = "select m from MatchData m where (m.team1 = ?1 or m.team2 = ?1) and m.date between ?2 and ?3 order by date desc")
    List<MatchData> findMatchesByTeamBetweenDates(String teamName, LocalDate startDate, LocalDate endDate);

    // List<MatchData> findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(String team1,
    //         LocalDate startDateOfTeam1, LocalDate endDateOfTeam1, String team2, LocalDate startDateOfTeam2,
    //         LocalDate endDateOfTeam2);
}