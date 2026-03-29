package com.sgaur_tech.google_places_mcp_server.client;

import java.util.Map;

public interface IPlacesClient {
    Map<String, Object> searchByQuery(String query, String fieldMask);

    Map<String, Object> getPlaceDetails(String placeId, String fieldMask);

    Map<String, Object> searchNearbyPlaces(double lat, double lng, String type, double radiusMeters);

}
