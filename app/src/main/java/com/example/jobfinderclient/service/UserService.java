package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.person.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/{id}")
    public Call<User> findById(@Path("id") Integer id);

    @PUT("users")
    public Call<User> updateUser(@Body User user);

    @GET("users/accounts/{id}")
    public Call<User> findByAccountId(@Path("id") Long accountId);
}
