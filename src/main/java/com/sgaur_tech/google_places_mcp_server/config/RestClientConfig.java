package com.sgaur_tech.google_places_mcp_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient googlePlacesRestClient(GooglePlacesConfig config) {
        return RestClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader(config.getHeaderMap().get("key"), config.getApiKey())
                .build();
    }
}
