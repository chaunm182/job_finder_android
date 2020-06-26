package com.example.jobfinderclient.ui.jobsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderclient.R;
import com.example.jobfinderclient.adapter.PostHomeAdapter;
import com.example.jobfinderclient.model.post.JobCategory;
import com.example.jobfinderclient.model.post.Post;
import com.example.jobfinderclient.viewmodel.JobSearchViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobSearchFragment extends Fragment implements SearchView.OnQueryTextListener,
        View.OnClickListener, FilterDialogFragment.FilterDialogListener {

    private JobSearchViewModel jobSearchViewModel;
    private boolean isTextInvisible = true;
    private String filterAddress = "all";
    private String filterJobCategory = "0";

    TextView tvSearchSize, tvSearchSize1;
    SearchView svJob;
    ImageView ivFilter;
    RecyclerView rvSearchResult;
    PostHomeAdapter postHomeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jobSearchViewModel =
                ViewModelProviders.of(this).get(JobSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_job_search, container, false);
        addControls(root);
        addEvents();

        jobSearchViewModel.getPostsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postHomeAdapter.setPosts(posts);
                if(!isTextInvisible){
                    tvSearchSize.setVisibility(View.VISIBLE);
                    tvSearchSize1.setVisibility(View.VISIBLE);
                    tvSearchSize.setText(String.valueOf(posts.size()));
                }

            }
        });

        return root;
    }

    private void addEvents() {
        svJob.setOnQueryTextListener(this);
        ivFilter.setOnClickListener(this);
    }

    public void addControls(View root){
        svJob = root.findViewById(R.id.svJob);
        svJob.setIconifiedByDefault(false);
        svJob.setFocusable(true);
        svJob.setIconified(false);

        tvSearchSize = root.findViewById(R.id.tvSearchSize);
        tvSearchSize.setVisibility(View.INVISIBLE);
        tvSearchSize1 = root.findViewById(R.id.tvSearchSize1);
        tvSearchSize1.setVisibility(View.INVISIBLE);
        ivFilter = root.findViewById(R.id.ivFilter);

        postHomeAdapter = new PostHomeAdapter(new ArrayList<>(),this.getActivity());
        rvSearchResult = root.findViewById(R.id.rvSearchJobResult);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false));
        rvSearchResult.setAdapter(postHomeAdapter);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        String jobName = query;

        Map<String,String> props = new HashMap<>();
        props.put("jobName",jobName);
        props.put("address",filterAddress);
        props.put("jobCategory.id",filterJobCategory);
        if (isTextInvisible) isTextInvisible = false;
        jobSearchViewModel.findByProps(props);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClick(View v) {
        DialogFragment filterDialog = new FilterDialogFragment(this,filterAddress,filterJobCategory);
        filterDialog.show(getParentFragmentManager(),"dialog");
    }

    @Override
    public void onPositiveClicked(String address, JobCategory jobCategory) {
        filterAddress = address;
        if(address.equals("Tất cả địa điểm")) filterAddress = "all";
        filterJobCategory = String.valueOf(jobCategory.getId());
        Toast.makeText(this.getActivity(),"Đã lưu",Toast.LENGTH_SHORT).show();
    }
}