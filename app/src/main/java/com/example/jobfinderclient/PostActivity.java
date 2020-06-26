package com.example.jobfinderclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.jobfinderclient.model.post.Post;

public class PostActivity extends AppCompatActivity {

    TextView tvJobName, tvCompanyName, tvSalary, tvAmount,tvAddress, tvExperience, tvDescription, tvRequirement, tvBenefit;
    Button btnApplyJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        addControls();
        showPost();
    }

    public void addControls(){
        tvJobName = findViewById(R.id.tvJobName);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvAmount = findViewById(R.id.tvAmount);
        tvSalary = findViewById(R.id.tvSalary);
        tvAddress = findViewById(R.id.tvAddress);
        tvExperience = findViewById(R.id.tvExperience);
        tvDescription = findViewById(R.id.tvPosition);
        tvRequirement = findViewById(R.id.tvRequirement);
        tvBenefit = findViewById(R.id.tvBenefit);
        btnApplyJob = findViewById(R.id.btnApplyJob);
    }

    public void showPost(){
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");
        tvJobName.setText(post.getJobName());
        tvCompanyName.setText(post.getCompany().getName());
        tvAddress.setText(post.getAddress());
        tvSalary.setText(post.getMinSalary()/1000000+" triệu - "+post.getMaxSalary()/1000000+" triệu");
        tvAmount.setText(post.getAmount().toString());
        tvExperience.setText(post.getExperience());
        tvDescription.setText(post.getDescription());
        tvRequirement.setText(post.getRequirement());
        tvBenefit.setText(post.getBenefit());
    }
}