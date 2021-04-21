package com.practice.ipldashboard.service;

import java.util.List;

import com.practice.ipldashboard.constant.ExceptionMessageConstants;
import com.practice.ipldashboard.exception.TeamNameNotFoundException;
import com.practice.ipldashboard.model.MatchData;
import com.practice.ipldashboard.model.Team;
import com.practice.ipldashboard.repository.MatchDataRepository;
import com.practice.ipldashboard.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchDataRepository matchDataRepository;

    public Team getTeamByName(String teamName) throws TeamNameNotFoundException {
        Team team = teamRepository.findByTeamName(teamName);
        if(team != null){
            Pageable pageable = PageRequest.of(0, 4);
            List<MatchData> matchDataOfTeam = matchDataRepository.findByTeam1OrTeam2OrderByDateDesc(teamName, teamName,
                    pageable);
            team.setMatches(matchDataOfTeam);
            return team;
        }
        else{
            throw new TeamNameNotFoundException(ExceptionMessageConstants.TEAM_NOT_FOUND_ERROR_MESSAGE);
        }
    }

}
