package com.example.campusgo.api;

import com.example.campusgo.models.Announcement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("posts")
    Call<List<Announcement>> getAnnouncements();
}