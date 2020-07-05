package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.RecruitmentDetailClient;
import com.example.jobfinderclient.model.post.RecruitmentDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {

    private MutableLiveData<RecruitmentDetail> recruitmentDetailLiveData;

    public PostViewModel() {
        recruitmentDetailLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RecruitmentDetail> getRecruitmentDetailLiveData() {
        return recruitmentDetailLiveData;
    }

    public void findRecruitmentDetailByPostIdAndUserId(Long postId, Long userId){
        RecruitmentDetailClient recruitmentDetailClient = RecruitmentDetailClient.getINSTANCE();
        recruitmentDetailClient.findRecruitmentDetailByPostIdAndUserId(postId,userId).enqueue(
                new Callback<RecruitmentDetail>() {
                    @Override
                    public void onResponse(Call<RecruitmentDetail> call, Response<RecruitmentDetail> response) {
                        if(response.isSuccessful()){
                            recruitmentDetailLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecruitmentDetail> call, Throwable t) {
                        Log.e("PostViewModel", "onFailure: ",t );
                    }
                }
        );
    }

    public void addRecruitmentDetail(RecruitmentDetail recruitmentDetail){
        RecruitmentDetailClient recruitmentDetailClient = RecruitmentDetailClient.getINSTANCE();
        recruitmentDetailClient.addRecruitmentDetail(recruitmentDetail).enqueue(
                new Callback<RecruitmentDetail>() {
                    @Override
                    public void onResponse(Call<RecruitmentDetail> call, Response<RecruitmentDetail> response) {
                        if(response.isSuccessful()){
                            recruitmentDetailLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecruitmentDetail> call, Throwable t) {
                        Log.e("PostViewModel", "onFailure: ",t );
                    }
                }
        );
    }
}
