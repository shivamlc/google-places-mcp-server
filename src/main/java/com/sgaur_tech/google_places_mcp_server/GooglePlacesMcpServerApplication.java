package com.sgaur_tech.google_places_mcp_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(com.sgaur_tech.google_places_mcp_server.config.GooglePlacesConfig.class)
public class GooglePlacesMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GooglePlacesMcpServerApplication.class, args);
	}

}
