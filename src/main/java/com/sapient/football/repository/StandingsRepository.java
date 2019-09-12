package com.sapient.football.repository;

import com.sapient.football.exceptions.FootBallException;
import com.sapient.football.pojo.FootBallLeagueResponse;
import com.sapient.football.pojo.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class StandingsRepository {

    @Autowired
    RestTemplate restTemplate;

    @Value("${standings_url}")
    String standings_url;

    public FootBallLeagueResponse[] getStandingsByLeagueId(String league_Id) throws FootBallException {
        String standingUrl = UriComponentsBuilder.fromUriString(standings_url)
                .replaceQueryParam("league_id", league_Id).toUriString();

        ResponseEntity<FootBallLeagueResponse[]> standingresponse = restTemplate.getForEntity(standingUrl,
                FootBallLeagueResponse[].class);
        return standingresponse.getBody();
    }
}
