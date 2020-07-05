package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.profile.Skill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SkillService {
    @GET("skills/profile/{profileId}")
    Call<List<Skill>> findSkillsByProfileId(@Path("profileId") Long profileId);
}
