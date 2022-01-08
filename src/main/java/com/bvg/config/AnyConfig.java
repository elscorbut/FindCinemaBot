package com.bvg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;

@Configuration
public class AnyConfig {

    @Bean("disableRedirectRestTemplate")
    RestTemplate getDisableRedirectRestTemplate() {

        return new RestTemplate(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
                connection.setInstanceFollowRedirects(false);
            }
        });
    }

    @Bean("restTemplate")
    RestTemplate getRestTemplate() {

        return new RestTemplate();
    }
}
