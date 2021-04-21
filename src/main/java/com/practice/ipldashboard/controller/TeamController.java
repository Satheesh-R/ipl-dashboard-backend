package com.practice.ipldashboard.controller;

import com.practice.ipldashboard.exception.TeamNameNotFoundException;
import com.practice.ipldashboard.model.Team;
import com.practice.ipldashboard.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/team/{teamName}")
    public Team getTeamByName(@PathVariable String teamName) throws TeamNameNotFoundException{
        return teamService.getTeamByName(teamName);
    }
}
