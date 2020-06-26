package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.post.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PostService {
    @GET("posts/all")
    Call<List<Post>> findAll();

    @GET("posts")
    Call<List<Post>> findByProps(@QueryMap Map<String,String> props);

    @GET("posts/all/address")
    Call<List<String>> findAllAddress();
}
