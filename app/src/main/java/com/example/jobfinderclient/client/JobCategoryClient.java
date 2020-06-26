package com.example.jobfinderclient.client;

import com.example.jobfinderclient.model.post.JobCategory;
import com.example.jobfinderclient.service.JobCategoryService;
import com.example.jobfinderclient.util.ApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class JobCategoryClient {
    private JobCategoryService jobCategoryService;
    private static JobCategoryClient INSTANCE;

    public static JobCategoryClient getINSTANCE() {
        if(INSTANCE==null) INSTANCE = new JobCategoryClient();
        return INSTANCE;
    }

    public JobCategoryClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();
        jobCategoryService = retrofit.create(JobCategoryService.class);
    }

    public Call<List<JobCategory>> findAll(){
        return jobCategoryService.findAll();
    }
}
