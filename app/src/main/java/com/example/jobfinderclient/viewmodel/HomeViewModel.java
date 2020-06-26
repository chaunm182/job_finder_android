package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.PostClient;
import com.example.jobfinderclient.model.post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Post>> listPostLiveData;

    public HomeViewModel() {
        listPostLiveData = new MutableLiveData<>();

    }

    public LiveData<List<Post>> getText() {
        return listPostLiveData;
    }

    public void findAll(){
        PostClient postClient = PostClient.getINSTANCE();
        postClient.findAll().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    listPostLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("HomeViewModel", "onFailure: ", t);

            }
        });
    }
}