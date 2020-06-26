package com.example.jobfinderclient.client;

import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.account.Account;
import com.example.jobfinderclient.service.AccountService;
import com.example.jobfinderclient.util.ApiUtil;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountClient {
    private AccountService accountService;
    private static AccountClient INSTANCE;

    public AccountClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();
        accountService = retrofit.create(AccountService.class);
    }

    public static AccountClient getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new AccountClient();
        }
        return INSTANCE;
    }

    public Call<Account> findAccountByUsernameAndPassword(String username, String password){
        return accountService.findAccountByUsernameAndPassword(username,password);
    }
}
