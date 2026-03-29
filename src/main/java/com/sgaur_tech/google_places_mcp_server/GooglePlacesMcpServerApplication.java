package com.sgaur_tech.google_places_mcp_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sgaur_tech.google_places_mcp_server.config.GooglePlacesConfig;

@SpringBootApplication
@EnableConfigurationProperties(GooglePlacesConfig.class)
public class GooglePlacesMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GooglePlacesMcpServerApplication.class, args);
	}

}
