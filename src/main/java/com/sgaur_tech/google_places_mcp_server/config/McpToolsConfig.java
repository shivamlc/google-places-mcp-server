package com.sgaur_tech.google_places_mcp_server.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sgaur_tech.google_places_mcp_server.service.GooglePlacesMcpToolsService;

@Configuration
public class McpToolsConfig {

    @Bean
    public ToolCallbackProvider placesTools(GooglePlacesMcpToolsService toolsService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(toolsService)
                .build();
    }
}
