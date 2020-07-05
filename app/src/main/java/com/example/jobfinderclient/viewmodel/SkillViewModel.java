package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.SkillClient;
import com.example.jobfinderclient.model.profile.Skill;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillViewModel extends ViewModel {
    private MutableLiveData<List<Skill>> skillsLiveData;

    public SkillViewModel() {
        skillsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Skill>> getSkillsLiveData() {
        return skillsLiveData;
    }

    public void findSkillsByProfileId(Long profileId){
        SkillClient skillClient = SkillClient.getINSTANCE();
        skillClient.findSkillsByProfileId(profileId).enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                if(response.isSuccessful()){
                    skillsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Log.e("SkillViewModel", "onFailure: ", t);
            }
        });
    }
}
