package com.example.jobfinderclient.client;

import com.example.jobfinderclient.model.person.User;
import com.example.jobfinderclient.service.UserService;
import com.example.jobfinderclient.util.ApiUtil;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserClient {

    private UserService userService;
    private static UserClient INSTANCE;

    public static UserClient getINSTANCE() {
        if(INSTANCE==null) INSTANCE = new UserClient();
        return INSTANCE;
    }

    public UserClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();
        userService = retrofit.create(UserService.class);
    }

    public Call<User> findById(Integer id){
        return userService.findById(id);
    }

    public Call<User> updateUser(User user){
        return userService.updateUser(user);
    }

    public Call<User> findByAccountId(Long accountId){
        return userService.findByAccountId(accountId);
    }
}
