package com.sgaur_tech.google_places_mcp_server.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;

@ConfigurationProperties(prefix = "google.places")
@Data
@Getter
public class GooglePlacesConfig {
    private String baseUrl;
    private Map<String, String> headerMap;
    private String apiKey;
}
