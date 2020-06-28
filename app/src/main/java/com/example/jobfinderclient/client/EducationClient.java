package com.example.jobfinderclient.client;

import com.example.jobfinderclient.model.profile.Education;
import com.example.jobfinderclient.service.EducationService;
import com.example.jobfinderclient.util.ApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class EducationClient {

    private EducationService educationService;
    private static EducationClient INSTANCE;

    public EducationClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();
        educationService = retrofit.create(EducationService.class);
    }

    public static EducationClient getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new EducationClient();
        }
        return INSTANCE;
    }

    public Call<List<Education>> findByProfileId(Long profileId){
        return educationService.findByProfileId(profileId);
    }

    public Call<Education> addEducation(Education education){
        return educationService.addEducation(education);
    }

    public Call<Void> deleteEducation(Integer id){
        return educationService.deleteEducationById(id);
    }

    public Call<Education> updateEducation(Education education){
        return educationService.updateEducation(education);
    }
}
