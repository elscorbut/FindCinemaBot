package com.bvg.service;

import com.bvg.dao.TmdbWatchlistDao;
import com.bvg.dto.TMDBWatchListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class TMDBService {

    @Autowired
    TmdbWatchlistDao tmdbWatchlistDao;

    @Value("${tmdb.host}")
    private String host;
    @Value("${tmdb.api-version}")
    private String apiVersion;
    @Value("${tmdb.token}")
    private String token;
    @Value("${tmdb.account-id}")
    private String accountId;
    @Value("${tmdb.other}")
    private String other;

    private RestTemplate restTemplate = new RestTemplate();

//    @GetMapping("/watchlist")
    public TMDBWatchListDto getWatchList() {
        TMDBWatchListDto body;
        try {
            HttpEntity<String> request = this.getHttpEntityByToken();
            ResponseEntity<TMDBWatchListDto> response = this.restTemplate.exchange(
                    this.host + this.apiVersion + this.accountId
                            + this.other, HttpMethod.GET, request, TMDBWatchListDto.class);

            body = response.getBody();
            assert body != null;
            tmdbWatchlistDao.updateTmdbWatchlist(body.getResults());

            //            serviceLog(httpServletRequest, body, "getInstitution", this.urlApiInstitution, true);
        }
        catch (Exception e) {
            //            serviceLog(httpServletRequest, body, "getInstitution", this.urlApiInstitution, false);
            throw e;
        }

        return body;
    }

    private HttpEntity<String> getHttpEntityByToken() {
        HttpHeaders header = new HttpHeaders();
        header.set("Host", this.host);
        header.set("Content-Type", "application/json; charset=utf-8");
        header.set("Authorization", "Bearer " + this.token);
        return new HttpEntity(header);
    }
}
