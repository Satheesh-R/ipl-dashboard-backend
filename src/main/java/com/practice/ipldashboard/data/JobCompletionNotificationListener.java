package com.practice.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import com.practice.ipldashboard.model.Team;
import com.practice.ipldashboard.repository.MatchDataRepository;
import com.practice.ipldashboard.repository.TeamRepository;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private MatchDataRepository matchDataRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("BATCH JOB FINISHED, VERIFICATION STARTED");
            jdbcTemplate.query("SELECT team1, team2, date FROM match_data",
                              (rs,row) -> "Team 1"+rs.getString(1) + " Team 2 "+rs.getString(2)+" Date "+rs.getString(3)
                              ).forEach(matchData -> log.info("Found < {} > in the databse",matchData));
        }
        log.info("INSERTION INTO TEAM TABLE STARTED");
        Map<String,Team> teamData = new HashMap<>();
        matchDataRepository.findDistinctTeam1().stream()
                      .map(teamMatchCount -> new Team((String) teamMatchCount[0],
                                                        Long.valueOf(teamMatchCount[1].toString())))
                      .forEach(team -> teamData.put(team.getTeamName(), team));
        matchDataRepository.findDistinctTeam2().stream()
                           .forEach(teamMatchCount -> {
                               Team team = teamData.get((String) teamMatchCount[0]);
                               team.setTotalMatches(team.getTotalMatches() + Long.valueOf(teamMatchCount[1].toString()));
                           });
        matchDataRepository.findTeamWins().stream()
                           .forEach(winCount ->{
                               Team team = teamData.get((String) winCount[0]);
                               if(team!=null) team.setTotalWins(Long.valueOf(winCount[1].toString()));
                           });
        teamData.forEach((teamName,teamDetails) -> teamRepository.save(teamDetails));
        log.info("DATA INSERTION INTO TEAM TABLE COMPLETED");
    }
}