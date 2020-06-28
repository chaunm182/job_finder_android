package com.example.jobfinderclient.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jobfinderclient.client.EducationClient;
import com.example.jobfinderclient.model.profile.Education;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationViewModel extends ViewModel {
    private MutableLiveData<List<Education>> educationsLiveData;

    public EducationViewModel() {
        educationsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Education>> getEducationsLiveData() {
        return educationsLiveData;
    }

    public void findAllEducationsByProfileId(Long profileId){
        EducationClient educationClient = EducationClient.getINSTANCE();
        educationClient.findByProfileId(profileId).enqueue(new Callback<List<Education>>() {
            @Override
            public void onResponse(Call<List<Education>> call, Response<List<Education>> response) {
                if(response.isSuccessful()){
                    educationsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Education>> call, Throwable t) {
                Log.e("EducationViewModel", "onFailure: ",t );
            }
        });
    }

    public void addEducation(Education education){
        EducationClient educationClient = EducationClient.getINSTANCE();
        educationClient.addEducation(education).enqueue(new Callback<Education>() {
            @Override
            public void onResponse(Call<Education> call, Response<Education> response) {
                if(response.isSuccessful()){
                    List<Education> educations = educationsLiveData.getValue();
                    educations.add(response.body());
                    educationsLiveData.setValue(educations);
                }
            }

            @Override
            public void onFailure(Call<Education> call, Throwable t) {
                Log.e("EducationViewModel", "onFailure: ",t );
            }
        });
    }

    public void deleteEducation(Integer id){
        EducationClient educationClient = EducationClient.getINSTANCE();
        educationClient.deleteEducation(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    List<Education> educations = educationsLiveData.getValue();
                    int removePosition = -1;
                    for(int i=0;i<educations.size();i++){
                        if(id==educations.get(i).getId()){
                            removePosition = i;
                            break;
                        }
                    }
                    educations.remove(removePosition);
                    educationsLiveData.setValue(educations);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("EducationViewModel", "onFailure: ",t );
            }
        });
    }

    public void updateEducation(Education education){
        EducationClient educationClient = EducationClient.getINSTANCE();
        educationClient.updateEducation(education).enqueue(new Callback<Education>() {
            @Override
            public void onResponse(Call<Education> call, Response<Education> response) {
                if(response.isSuccessful()){
                    List<Education> educations = educationsLiveData.getValue();
                    Education result = response.body();
                    int position = -1;
                    for(int i=0;i<educations.size();i++){
                        if(result.getId()==educations.get(i).getId()){
                            position = i;
                            break;
                        }
                    }
                    educations.set(position,result);
                    educationsLiveData.setValue(educations);
                }
            }

            @Override
            public void onFailure(Call<Education> call, Throwable t) {
                Log.e("EducationViewModel", "onFailure: ",t );
            }
        });
    }
}
