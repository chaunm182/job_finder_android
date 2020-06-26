package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.post.Subscriber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SubscriberService {
    @GET("subscribers/{userId}")
    public Call<List<Subscriber>> findSubscribersByUserId(@Path("userId")Long userId);

    @POST("subscribers")
    public Call<Subscriber> addSubscriber(@Body Subscriber subscriber);


}
