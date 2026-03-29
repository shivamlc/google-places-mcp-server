package com.sgaur_tech.google_places_mcp_server.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sgaur_tech.google_places_mcp_server.client.IPlacesClient;

import tools.jackson.databind.ObjectMapper;

@Service
public class GooglePlacesMcpToolsService implements IPlacesMcpService {
    private final IPlacesClient placesClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GooglePlacesMcpToolsService(@Qualifier("googlePlacesClient") IPlacesClient placesClient) {
        this.placesClient = placesClient;
    }

    @Tool(description = """
            Search for a place by name or address using the Google Places API.
            Returns place IDs, names, addresses, and ratings.
            Use the returned place ID with get_place_reviews to fetch reviews.
            """)
    @Override
    public String searchPlace(
            @ToolParam(description = "Place name or address, e.g. 'Crown Casino Melbourne'") String query) {
        try {
            var result = placesClient.searchByQuery(
                    query,
                    "places.id,places.displayName,places.formattedAddress,places.rating,places.userRatingCount");
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(result.get("places"));
        } catch (Exception e) {
            return "Error searching for place: " + e.getMessage();
        }
    }

    @Tool(description = """
            Get reviews for a specific place using its Google Place ID.
            Returns up to 5 reviews (Google API limit), overall rating,
            and total number of ratings.
            """)
    @Override
    public String getPlaceReviews(
            @ToolParam(description = "Google Place ID, e.g. 'places/ChIJN1t_tDeuEmsRUsoyG83frY4'") String placeId,
            @ToolParam(description = "Maximum number of reviews to return (1-5)") int maxReviews) {
        try {
            var data = placesClient.getPlaceDetails(
                    placeId,
                    "reviews,rating,userRatingCount,displayName");

            List<Map<String, Object>> reviews = ((List<?>) data.getOrDefault("reviews", List.of()))
                    .stream()
                    .limit(Math.min(maxReviews, 5))
                    .map(r -> {
                        @SuppressWarnings("unchecked")
                        var review = (Map<String, Object>) r;
                        @SuppressWarnings("unchecked")
                        var author = (Map<String, Object>) review.get("authorAttribution");
                        @SuppressWarnings("unchecked")
                        var text = (Map<String, Object>) review.get("text");
                        return Map.<String, Object>of(
                                "author", author != null ? author.get("displayName") : "Unknown",
                                "rating", review.getOrDefault("rating", 0),
                                "review", text != null ? text.get("text") : "",
                                "when", review.getOrDefault("relativePublishTimeDescription", ""));
                    })
                    .toList();

            var summary = Map.of(
                    "name", data.getOrDefault("displayName", Map.of()),
                    "overallRating", data.getOrDefault("rating", 0),
                    "totalRatings", data.getOrDefault("userRatingCount", 0),
                    "reviews", reviews);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(summary);
        } catch (Exception e) {
            return "Error fetching reviews: " + e.getMessage();
        }
    }

    @Tool(description = """
            Find nearby places of a given type and return their ratings and reviews.
            Useful for comparing competitors, finding the best coffee shop nearby, etc.
            """)
    @Override
    public String getNearbyPlacesWithReviews(
            @ToolParam(description = "Latitude of the center point") double latitude,
            @ToolParam(description = "Longitude of the center point") double longitude,
            @ToolParam(description = "Place type, e.g. restaurant, cafe, hotel, bar") String placeType,
            @ToolParam(description = "Search radius in meters (max 50000)") double radiusMeters) {
        try {
            var result = placesClient.searchNearbyPlaces(latitude, longitude, placeType, radiusMeters);
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(result.get("places"));
        } catch (Exception e) {
            return "Error fetching nearby places: " + e.getMessage();
        }
    }

}
