package com.sgaur_tech.google_places_mcp_server.service;

public interface IPlacesMcpService {
    public String searchPlace(String query);

    public String getPlaceReviews(String placeId, int maxReviews);

    public String getNearbyPlacesWithReviews(double latitude, double longitude, String placeType, double radiusMeters);

}
