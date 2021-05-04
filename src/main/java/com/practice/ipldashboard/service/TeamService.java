package com.practice.ipldashboard.service;

import java.util.List;

import com.practice.ipldashboard.exception.TeamNameNotFoundException;
import com.practice.ipldashboard.model.MatchData;
import com.practice.ipldashboard.model.Team;

public interface TeamService {
    Team getTeamByName(String teamName) throws TeamNameNotFoundException;

    List<MatchData> getMatchesForTeam(String teamName, int year);

    List<Team> getAllTeams();
}
