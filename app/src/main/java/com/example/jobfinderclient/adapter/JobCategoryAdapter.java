package com.example.jobfinderclient.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.jobfinderclient.model.post.JobCategory;

import java.util.List;

public class JobCategoryAdapter extends ArrayAdapter<JobCategory> {
    public JobCategoryAdapter(@NonNull Context context, int resource, @NonNull List<JobCategory> objects) {
        super(context, resource, objects);
    }
}
