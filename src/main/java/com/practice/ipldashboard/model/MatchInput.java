package com.practice.ipldashboard.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MatchInput {
   private Long id;
   private String city;
   private String date;
   private String playerOfMatch;
   private String venue;
   private String neutralVenue;
   private String team1;
   private String team2;
   private String tossWinner;
   private String tossDecision;
   private String matchWinner;
   private String result;
   private String resultMargin;
   private String eliminator;
   private String method;
   private String umpire1;
   private String umpire2;
}