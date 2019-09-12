package com.sapient.football.service;

import com.sapient.football.exceptions.FootBallException;
import com.sapient.football.pojo.Country;
import com.sapient.football.pojo.FootBallLeagueResponse;
import com.sapient.football.pojo.League;
import com.sapient.football.repository.CountryRepository;
import com.sapient.football.repository.LeagueRepostory;
import com.sapient.football.repository.StandingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodBallService {

    @Autowired
    CountryRepository countryRepo;

    @Autowired
    LeagueRepostory leagueRepo;

    @Autowired
    StandingsRepository standingsRepo;


    public List<FootBallLeagueResponse> getStandingsByCountry(String countryName)
            throws FootBallException {

        Country country = countryRepo.getCountryByName(countryName);
        List<League> leagues = leagueRepo.getLeagueByCountryId(country.getCountry_id());

        List<FootBallLeagueResponse> finalList = new ArrayList<>();

        for (League league : leagues) {
            for( FootBallLeagueResponse standings : standingsRepo.getStandingsByLeagueId(league.getLeague_id())){
                finalList.add(standings);
            }
        }


        return getLeagueResponse(country, leagues, finalList);
    }


    public List<FootBallLeagueResponse> getStandingsByLeague(String countryName, String leagueName)
            throws FootBallException {

        Country country = countryRepo.getCountryByName(countryName);
        List<League> leagues = leagueRepo.getLeagueByleagueName(leagueName);
        List<FootBallLeagueResponse> finalList = new ArrayList<>();

        for (League league : leagues) {
            for( FootBallLeagueResponse standings : standingsRepo.getStandingsByLeagueId(league.getLeague_id())){
                finalList.add(standings);
            }
        }


        return getLeagueResponse(country, leagues, finalList);
    }

    public List<FootBallLeagueResponse> getStandingsByTeamName(String countryName, String teamName)
            throws FootBallException {

        Country country = countryRepo.getCountryByName(countryName);
        List<League> leagues = leagueRepo.getLeagueByCountryId(country.getCountry_id());
        List<FootBallLeagueResponse> finalList = new ArrayList<>();

        for (League league : leagues) {
            for( FootBallLeagueResponse standings : standingsRepo.getStandingsByLeagueId(league.getLeague_id())){
                finalList.add(standings);
            }
        }

        return getLeagueResponse(country, leagues, finalList);

    }

    private List<FootBallLeagueResponse> getLeagueResponse(Country country, List<League> leagues, List<FootBallLeagueResponse> standings) throws FootBallException{

        List<FootBallLeagueResponse> list = new ArrayList<>();
        FootBallLeagueResponse leagueResponse = null;

        for (League league : leagues) {
            for (FootBallLeagueResponse standing : standings) {
                if (standing.getCountry_name().equalsIgnoreCase(country.getCountry_name())) {
                    leagueResponse = new FootBallLeagueResponse();
                    leagueResponse.setCountry_id(country.getCountry_id());
                    leagueResponse.setCountry_name(standing.getCountry_name());
                    leagueResponse.setLeague_name(standing.getTeam_name());
                    leagueResponse.setLeague_id(league.getLeague_id());
                    leagueResponse.setTeam_id(standing.getTeam_id());
                    leagueResponse.setTeam_name(standing.getTeam_name());
                    leagueResponse.setOverall_league_position(standing.getOverall_league_position());
                    list.add(leagueResponse);
                }
            }
        }

        return list;
    }
}
