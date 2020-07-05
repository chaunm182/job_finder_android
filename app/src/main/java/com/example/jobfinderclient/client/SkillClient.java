package com.example.jobfinderclient.client;

import com.example.jobfinderclient.model.profile.Skill;
import com.example.jobfinderclient.service.SkillService;
import com.example.jobfinderclient.util.ApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class SkillClient {
    private SkillService skillService;
    private static SkillClient INSTANCE;

    public SkillClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();
        skillService = retrofit.create(SkillService.class);
    }

    public static SkillClient getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new SkillClient();
        }
        return INSTANCE;
    }

    public Call<List<Skill>> findSkillsByProfileId(Long profileId){
        return skillService.findSkillsByProfileId(profileId);
    }
}
