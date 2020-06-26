package com.example.jobfinderclient.client;

import com.example.jobfinderclient.model.post.Subscriber;
import com.example.jobfinderclient.service.SubscriberService;
import com.example.jobfinderclient.util.ApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class SubscriberClient {

    private SubscriberService subscriberService;
    private static SubscriberClient INSTANCE;

    public SubscriberClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();
        subscriberService = retrofit.create(SubscriberService.class);
    }

    public static SubscriberClient getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new SubscriberClient();
        }
        return INSTANCE;
    }

    public Call<List<Subscriber>> findByUserId(Long userId){
        return subscriberService.findSubscribersByUserId(userId);
    }

    public Call<Subscriber> addSubscriber(Subscriber subscriber){
        return subscriberService.addSubscriber(subscriber);
    }

}
