package com.example.jobfinderclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jobfinderclient.adapter.EducationAdapter;
import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.profile.Education;
import com.example.jobfinderclient.viewmodel.EducationViewModel;

import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity implements EducationAdapter.OnEducationListener {

    private EducationViewModel educationViewModel;

    RecyclerView rvEducations;
    EducationAdapter educationAdapter;

    ImageView ivAddEducation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        educationViewModel = ViewModelProviders.of(this).get(EducationViewModel.class);

        addControls();
        addEvents();
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_ACCOUNT_SESSION,MODE_PRIVATE);
        Long profileId=  sharedPreferences.getLong("profileId",0);
        educationViewModel.findAllEducationsByProfileId(profileId);
        educationViewModel.getEducationsLiveData().observe(this, new Observer<List<Education>>() {
            @Override
            public void onChanged(List<Education> educations) {
                educationAdapter.setEducations(educations);
            }
        });

    }

    public void addControls(){
        rvEducations = findViewById(R.id.rvEducations);
        educationAdapter = new EducationAdapter(new ArrayList<>(),this);
        rvEducations.setAdapter(educationAdapter);
        rvEducations.setLayoutManager(new LinearLayoutManager(this));

        ivAddEducation = findViewById(R.id.ivAddEducation);
    }

    public void addEvents(){
        ivAddEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationActivity.this,SaveEducationActivity.class);
                startActivityForResult(intent,Constant.REQUEST_CODE_ADD_EDUCATION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode==Constant.REQUEST_CODE_ADD_EDUCATION){
                Education education = (Education) data.getSerializableExtra("education");
                educationViewModel.addEducation(education);
            }
            else if(requestCode==Constant.REQUEST_CODE_UPDATE_EDUCATION){
                Education education = (Education) data.getSerializableExtra("education");
                educationViewModel.updateEducation(education);

            }

        }

    }

    @Override
    public void onDeleteClicked(Education educationClicked) {
        educationViewModel.deleteEducation(educationClicked.getId());
    }

    @Override
    public void onUpdateClicked(Education educationClicked) {
        Intent intent = new Intent(this, SaveEducationActivity.class);
        intent.putExtra("education",educationClicked);
        startActivityForResult(intent,Constant.REQUEST_CODE_UPDATE_EDUCATION);
    }
}