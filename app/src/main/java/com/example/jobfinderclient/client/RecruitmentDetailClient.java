package com.example.jobfinderclient.client;

import com.example.jobfinderclient.model.post.RecruitmentDetail;
import com.example.jobfinderclient.service.RecruitmentDetailService;
import com.example.jobfinderclient.util.ApiUtil;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RecruitmentDetailClient {

    private RecruitmentDetailService recruitmentDetailService;
    private static RecruitmentDetailClient INSTANCE;

    public RecruitmentDetailClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();

        recruitmentDetailService = retrofit.create(RecruitmentDetailService.class);
    }

    public static RecruitmentDetailClient getINSTANCE() {
        if(INSTANCE==null) INSTANCE = new RecruitmentDetailClient();
        return INSTANCE;
    }

    public Call<RecruitmentDetail> findRecruitmentDetailByPostIdAndUserId(Long postId, Long userId){
        return recruitmentDetailService.findByPostIdAndUserId(postId,userId);
    }

    public Call<RecruitmentDetail> addRecruitmentDetail(RecruitmentDetail recruitmentDetail){
        return recruitmentDetailService.addRecruitmentDetail(recruitmentDetail);
    }


}
