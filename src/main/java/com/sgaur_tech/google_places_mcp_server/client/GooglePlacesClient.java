package com.sgaur_tech.google_places_mcp_server.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GooglePlacesClient implements IPlacesClient {
    private final RestClient restClientConfig;

    public GooglePlacesClient(@Qualifier("googlePlacesRestClient") RestClient restClientConfig) {
        this.restClientConfig = restClientConfig;
    }

    @Override
    public Map<String, Object> searchByQuery(String query, String fieldMask) {
        return restClientConfig.post()
                .uri("/places:searchText")
                .header("X-Goog-FieldMask", fieldMask)
                .body(Map.of("textQuery", query))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public Map<String, Object> getPlaceDetails(String placeId, String fieldMask) {
        return restClientConfig.get()
                .uri("/places/{placeId}", placeId)
                .header("X-Goog-FieldMask", fieldMask)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public Map<String, Object> searchNearbyPlaces(
            double lat, double lng, String type, double radiusMeters) {
        return restClientConfig.post()
                .uri("/places:searchNearby")
                .header("X-Goog-FieldMask",
                        "places.id,places.displayName,places.rating,places.userRatingCount,places.reviews")
                .body(Map.of(
                        "includedTypes", List.of(type),
                        "locationRestriction", Map.of(
                                "circle", Map.of(
                                        "center", Map.of("latitude", lat, "longitude", lng),
                                        "radius", radiusMeters))))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

}
