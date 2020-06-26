package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.PostClient;
import com.example.jobfinderclient.model.post.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobSearchViewModel extends ViewModel {

    private MutableLiveData<List<Post>> postsMutableLiveData;

    public JobSearchViewModel() {
        postsMutableLiveData = new MutableLiveData<>();
    }

    public void findByProps(Map<String,String> props){
        PostClient client = PostClient.getINSTANCE();
        client.findByProp(props).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    List<Post> result = response.body();
                    postsMutableLiveData.setValue(result);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("JobSearchViewModel", "onFailure: ",t );
            }
        });
    }

    public MutableLiveData<List<Post>> getPostsMutableLiveData() {
        return postsMutableLiveData;
    }
}