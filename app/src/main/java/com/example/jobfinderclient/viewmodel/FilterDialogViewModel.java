package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.JobCategoryClient;
import com.example.jobfinderclient.client.PostClient;
import com.example.jobfinderclient.model.post.JobCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterDialogViewModel extends ViewModel {
    private MutableLiveData<List<String>> addressListMutableLiveData;
    private MutableLiveData<List<JobCategory>> jobCategoriesListMutableLiveData;

    public FilterDialogViewModel() {
        addressListMutableLiveData = new MutableLiveData<>();
        jobCategoriesListMutableLiveData = new MutableLiveData<>();
    }

    public void findAllAddress(){
        PostClient postClient = PostClient.getINSTANCE();
        postClient.findAllAddress().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    addressListMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("FilterDialogViewModel", "onFailure: ", t);
            }
        });
    }

    public void findAllJobCategories(){
        JobCategoryClient jobCategoryClient = JobCategoryClient.getINSTANCE();
        jobCategoryClient.findAll().enqueue(new Callback<List<JobCategory>>() {
            @Override
            public void onResponse(Call<List<JobCategory>> call, Response<List<JobCategory>> response) {
                if(response.isSuccessful()){
                    jobCategoriesListMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JobCategory>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<String>> getAddressListMutableLiveData() {
        return addressListMutableLiveData;
    }

    public MutableLiveData<List<JobCategory>> getJobCategoriesListMutableLiveData() {
        return jobCategoriesListMutableLiveData;
    }
}
