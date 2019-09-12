package com.sapient.football.repository;

import com.sapient.football.exceptions.FootBallException;
import com.sapient.football.pojo.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeagueRepostory {

    @Autowired
    RestTemplate restTemplate;

    @Value("${leagues_url}")
    String leagues_url;

    public List<League> getLeagueByCountryId(String country_Id) throws FootBallException {
        List<League> leagues = new ArrayList<>();
        ResponseEntity<League[]> responseLeagueList = restTemplate.getForEntity(leagues_url, League[].class);
        for (League league : responseLeagueList.getBody()) {
            if(league.getCountry_id().equalsIgnoreCase(country_Id)){
                leagues.add(league);
            }
        }
        return leagues;
    }

    public List<League> getLeagueByleagueName(String leagueName) throws FootBallException {
        List<League> leagues = new ArrayList<>();
        ResponseEntity<League[]> responseLeagueList = restTemplate.getForEntity(leagues_url, League[].class);
        for (League league : responseLeagueList.getBody()) {
            if(league.getLeague_name().equalsIgnoreCase(leagueName)){
                leagues.add(league);
            }
        }
        return leagues;
    }

}