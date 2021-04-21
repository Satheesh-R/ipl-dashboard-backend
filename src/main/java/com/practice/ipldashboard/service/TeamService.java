package com.practice.ipldashboard.service;

import com.practice.ipldashboard.exception.TeamNameNotFoundException;
import com.practice.ipldashboard.model.Team;

public interface TeamService {
    Team getTeamByName(String teamName) throws TeamNameNotFoundException;
}
