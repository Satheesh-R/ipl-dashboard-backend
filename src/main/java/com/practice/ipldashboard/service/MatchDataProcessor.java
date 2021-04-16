package com.practice.ipldashboard.service;

import com.practice.ipldashboard.model.MatchData;
import com.practice.ipldashboard.model.MatchInput;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchDataProcessor implements ItemProcessor<MatchInput,MatchData> {
    
    @Override
    public MatchData process(final MatchInput matchInput) throws Exception {
        String firstInningsTeam,secondInningsTeam; 
        MatchData matchData = new MatchData();
        if(matchInput.getTossDecision().equals("bat")){
            firstInningsTeam = matchInput.getTossWinner();
            secondInningsTeam = matchInput.getTossWinner().equals(matchInput.getTeam1()) 
            ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        else{
            secondInningsTeam = matchInput.getTossWinner();
            firstInningsTeam = matchInput.getTossWinner().equals(matchInput.getTeam1()) 
            ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        matchData.setId(matchInput.getId());
        matchData.setCity(matchInput.getCity());
        matchData.setDate(matchData.getDate());
        matchData.setNeutralVenue(matchInput.getNeutralVenue());
        matchData.setPlayerOfMatch(matchInput.getPlayerOfMatch());
        matchData.setResult(matchInput.getPlayerOfMatch());
        matchData.setResult(matchInput.getResult());
        matchData.setResultMargin(matchInput.getResultMargin());
        matchData.setTeam1(firstInningsTeam);
        matchData.setTeam2(secondInningsTeam);
        matchData.setTossDecision(matchInput.getTossDecision());
        matchData.setTossWinner(matchInput.getTossWinner());
        matchData.setUmpire1(matchInput.getUmpire1());
        matchData.setUmpire2(matchInput.getUmpire2());
        matchData.setVenue(matchInput.getVenue());
        matchData.setWinner(matchInput.getWinner());
        log.info("Converting {} to {}",matchInput,matchData);
        return matchData;
    }
    
}
