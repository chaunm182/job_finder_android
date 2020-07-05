package com.example.jobfinderclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.jobfinderclient.adapter.SkillAdapter;
import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.profile.Skill;
import com.example.jobfinderclient.viewmodel.SkillViewModel;

import java.util.ArrayList;
import java.util.List;

public class SkillActivity extends AppCompatActivity {
    private SkillViewModel skillViewModel;

    RecyclerView rvSkills;
    SkillAdapter skillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        skillViewModel = ViewModelProviders.of(this).get(SkillViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_ACCOUNT_SESSION,MODE_PRIVATE);
        Long profileId = sharedPreferences.getLong("profileId",0);
        skillViewModel.findSkillsByProfileId(profileId);
        skillViewModel.getSkillsLiveData().observe(this, new Observer<List<Skill>>() {
            @Override
            public void onChanged(List<Skill> skills) {
                skillAdapter.setSkills(skills);
            }
        });
        addControls();
    }

    public void addControls(){
        rvSkills = findViewById(R.id.rvSkill);
        skillAdapter = new SkillAdapter(new ArrayList<>());
        rvSkills.setAdapter(skillAdapter);
        rvSkills.setLayoutManager(new LinearLayoutManager(this));
    }
}