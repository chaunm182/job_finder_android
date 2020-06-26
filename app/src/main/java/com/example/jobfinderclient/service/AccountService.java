package com.example.jobfinderclient.service;

import com.example.jobfinderclient.model.account.Account;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccountService {
    @GET("accounts/{username},{password}")
    Call<Account> findAccountByUsernameAndPassword(@Path("username") String username,
                                                   @Path("password") String password);
}
