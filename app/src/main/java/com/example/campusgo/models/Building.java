package com.example.campusgo.models;

import java.io.Serializable;

public class Building implements Serializable {

    public String name;
    public String description;
    public String category;
    public String timing;

    public double lat;
    public double lng;

    public String emoji;

    public Building(
            String name,
            String description,
            String category,
            String timing,
            double lat,
            double lng,
            String emoji) {

        this.name = name;
        this.description = description;
        this.category = category;
        this.timing = timing;
        this.lat = lat;
        this.lng = lng;
        this.emoji = emoji;
    }
}