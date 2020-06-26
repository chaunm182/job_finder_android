package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.UserClient;
import com.example.jobfinderclient.model.person.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> userMutableLiveData;

    public ProfileViewModel() {
        userMutableLiveData = new MutableLiveData<>();
    }


    public void findByAccountId(Long accountId){
        UserClient userClient = UserClient.getINSTANCE();
        userClient.findByAccountId(accountId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    userMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ProfileViewModel", "onFailure: ", t);
            }
        });
    }

    public void updateUser(User user){
        UserClient userClient = UserClient.getINSTANCE();
        userClient.updateUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    userMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ProfileViewModel", "onFailure: ", t);
            }
        });
    }


    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}