package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.post.RecruitmentDetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecruitmentDetailService {

    @GET("recruitmentDetails/{postId},{userId}")
    public Call<RecruitmentDetail> findByPostIdAndUserId(@Path("postId")Long postId,
                                                         @Path("userId")Long userId);

    @POST("recruitmentDetails")
    public Call<RecruitmentDetail> addRecruitmentDetail(@Body RecruitmentDetail recruitmentDetail);
}
