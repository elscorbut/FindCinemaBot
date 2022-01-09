package com.bvg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

    @Bean("chromeDriver")
    String getChromeDriverPath(@Value("${driver.chromedriver}") Resource resource) throws IOException {

        return resource.getURL().getPath();
    }

    @Bean("fireFoxDriver")
    String getFireFoxDriverPath(@Value("${driver.firefoxdriver}") Resource resource) throws IOException {

        return resource.getURL().getPath();
    }
}
