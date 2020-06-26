package com.example.jobfinderclient.client;

import com.example.jobfinderclient.model.post.Post;
import com.example.jobfinderclient.service.PostService;
import com.example.jobfinderclient.util.ApiUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PostClient {

    private PostService postService;
    private static PostClient INSTANCE;

    public PostClient() {
        Retrofit retrofit = ApiUtil.getRetrofit();
        postService  = retrofit.create(PostService.class);
    }

    public static PostClient getINSTANCE() {
        if(INSTANCE==null) INSTANCE = new PostClient();
        return INSTANCE;
    }

    public Call<List<Post>> findAll(){
        return postService.findAll();
    }

    public Call<List<Post>> findByProp(Map<String,String> props){
        return postService.findByProps(props);
    }

    public Call<List<String>> findAllAddress(){
        return postService.findAllAddress();
    }


}
