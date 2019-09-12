package com.sapient.football.controller;

import com.sapient.football.exceptions.FootBallException;
import com.sapient.football.pojo.FootBallLeagueResponse;
import com.sapient.football.pojo.League;
import com.sapient.football.repository.LeagueRepostory;
import com.sapient.football.repository.StandingsRepository;
import com.sapient.football.service.FoodBallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodBallController {

    private static final Logger logger = LoggerFactory.getLogger(FoodBallController.class);

    @Autowired
    FoodBallService foodBallService;

    @GetMapping("/standings")
    public ResponseEntity<List<FootBallLeagueResponse>> getStatus(@RequestParam(value = "countryName") final String countryName
                                                                    ,@RequestParam(value = "leagueName", required=false) final String  leagueName
                                                                    ,@RequestParam(value = "teamName", required=false) final String teamName) throws FootBallException {
        List<FootBallLeagueResponse> response = null;
        try {
            if(!StringUtils.isEmpty(countryName) && StringUtils.isEmpty(leagueName) && StringUtils.isEmpty(teamName)){
                response = foodBallService.getStandingsByCountry(countryName);
            }

            if(!StringUtils.isEmpty(countryName) && !StringUtils.isEmpty(leagueName) ){
                response = foodBallService.getStandingsByLeague(countryName, leagueName);
            }

            if(!StringUtils.isEmpty(countryName) && !StringUtils.isEmpty(teamName) ){
                response = foodBallService.getStandingsByTeamName(countryName, teamName);
            }

            return new ResponseEntity<List<FootBallLeagueResponse>>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error occurred", ex);
            return (ResponseEntity<List<FootBallLeagueResponse>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
