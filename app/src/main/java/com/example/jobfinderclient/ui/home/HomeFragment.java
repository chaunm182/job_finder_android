package com.example.jobfinderclient.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobfinderclient.LoginActivity;
import com.example.jobfinderclient.R;
import com.example.jobfinderclient.adapter.PostHomeAdapter;
import com.example.jobfinderclient.model.post.Post;
import com.example.jobfinderclient.viewmodel.HomeViewModel;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RecyclerView rvPosts;
    Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.findAll();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        addControls(root);
        PostHomeAdapter postHomeAdapter = new PostHomeAdapter(new ArrayList<>(), this.getActivity());
        rvPosts.setLayoutManager(new LinearLayoutManager(root.getContext(),RecyclerView.HORIZONTAL,false));
        rvPosts.setAdapter(postHomeAdapter);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postHomeAdapter.setPosts(posts);
            }
        });

        final ImageView vBanner = root.findViewById(R.id.imageView);
        Glide.with(root.getContext())
                .load(getResources().getDrawable(R.drawable.banner)).fitCenter().into(vBanner);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessToken.getCurrentAccessToken()!=null){
                    LoginManager.getInstance().logOut();
                }
                Intent intent = new Intent(HomeFragment.this.getContext(), LoginActivity.class);
                startActivity(intent);
                HomeFragment.this.getActivity().finish();

            }
        });
    }

    private void addControls(View root){
        rvPosts = root.findViewById(R.id.rvNewPost);
        btnLogout = root.findViewById(R.id.btnLogout);
    }

}