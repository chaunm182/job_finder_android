package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.JobCategoryClient;
import com.example.jobfinderclient.client.SubscriberClient;
import com.example.jobfinderclient.model.post.JobCategory;
import com.example.jobfinderclient.model.post.Subscriber;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<List<JobCategory>> jobCategoryLiveData;
    private MutableLiveData<List<Subscriber>> subscribersLiveData;

    public NotificationsViewModel() {
        jobCategoryLiveData = new MutableLiveData<>();
        subscribersLiveData = new MutableLiveData<>();
    }

    public LiveData<List<JobCategory>> getJobCategoryLiveData() {
        return jobCategoryLiveData;
    }

    public MutableLiveData<List<Subscriber>> getSubscribersLiveData() {
        return subscribersLiveData;
    }

    public void findAllJobCategories(){
        JobCategoryClient jobCategoryClient = JobCategoryClient.getINSTANCE();
        jobCategoryClient.findAll().enqueue(new Callback<List<JobCategory>>() {
            @Override
            public void onResponse(Call<List<JobCategory>> call, Response<List<JobCategory>> response) {
                if(response.isSuccessful()){
                    jobCategoryLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JobCategory>> call, Throwable t) {

            }
        });
    }

    public void findSubscribersByUserId(Long userId){
        SubscriberClient subscriberClient = SubscriberClient.getINSTANCE();
        subscriberClient.findByUserId(userId).enqueue(new Callback<List<Subscriber>>() {
            @Override
            public void onResponse(Call<List<Subscriber>> call, Response<List<Subscriber>> response) {
                if(response.isSuccessful()){
                    subscribersLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Subscriber>> call, Throwable t) {

            }
        });
    }

    public void addSubscriber(Subscriber subscriber){
        SubscriberClient subscriberClient = SubscriberClient.getINSTANCE();
        subscriberClient.addSubscriber(subscriber).enqueue(new Callback<Subscriber>() {
            @Override
            public void onResponse(Call<Subscriber> call, Response<Subscriber> response) {
                if(response.isSuccessful()){
                    Subscriber result = response.body();
                    List<Subscriber> subscribers = subscribersLiveData.getValue();
                    subscribers.add(result);
                    subscribersLiveData.setValue(subscribers);
                }
            }

            @Override
            public void onFailure(Call<Subscriber> call, Throwable t) {
                Log.e("NotificationViewModel", "onFailure: ",t );
            }
        });
    }
}