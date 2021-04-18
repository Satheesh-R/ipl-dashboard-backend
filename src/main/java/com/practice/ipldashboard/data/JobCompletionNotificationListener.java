package com.practice.ipldashboard.data;

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
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("BATCH JOB FINISHED, VERIFICATION STARTED");
            jdbcTemplate.query("SELECT team1, team2, date FROM matchdata",
                              (rs,row) -> "Team 1"+rs.getString(1) + " Team 2 "+rs.getString(2)+" Date "+rs.getString(3)
                              ).forEach(matchData -> log.info("Found < {} > in the databse",matchData));
        }
    }
}