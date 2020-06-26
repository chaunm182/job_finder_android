package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.post.JobCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JobCategoryService {

    @GET("jobCategories")
    Call<List<JobCategory>> findAll();
}
