package com.sapient.football.repository;

import com.sapient.football.exceptions.FootBallException;
import com.sapient.football.pojo.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CountryRepository {

    @Autowired
    RestTemplate restTemplate;

    @Value("${countries_url}")
    String countries_url;

    public Country getCountryByName(String countryName) throws FootBallException {

        ResponseEntity<Country[]> responsecountryList = restTemplate.getForEntity(countries_url, Country[].class);
        for (Country country : responsecountryList.getBody()) {
            System.out.println("country == " + country.toString());
            if (countryName.equalsIgnoreCase(country.getCountry_name())) {
                return country;
            }
        }
        return null;

    }
}
