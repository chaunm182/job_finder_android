package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.profile.Education;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EducationService {

    @GET("educations/profile/{profileId}")
    Call<List<Education>> findByProfileId(@Path("profileId") Long profileId);

    @POST("educations")
    Call<Education> addEducation(@Body Education education);

    @DELETE("educations/{id}")
    Call<Void> deleteEducationById(@Path("id") Integer id);

    @PUT("educations")
    Call<Education> updateEducation(@Body Education education);
}
