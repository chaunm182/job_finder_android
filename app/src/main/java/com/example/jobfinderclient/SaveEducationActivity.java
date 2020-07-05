package com.example.jobfinderclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.profile.Education;
import com.example.jobfinderclient.model.profile.Profile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveEducationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etSchoolName, etStatAt, etEndAt, etDescription, etId;
    Button btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_education);
        addControls();
        addEvents();
        showEducation();
    }

    public void addControls(){
        etId = findViewById(R.id.etId);
        etSchoolName = findViewById(R.id.etSchoolName);
        etStatAt = findViewById(R.id.etStatAt);
        etEndAt = findViewById(R.id.etEndAt);
        etDescription = findViewById(R.id.etDescription);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    public void addEvents(){
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    public void showEducation(){
        Intent intent = getIntent();
        Education education = (Education) intent.getSerializableExtra("education");
        if(education!=null){
            etId.setText(education.getId().toString());
            etSchoolName.setText(education.getSchoolName().toString());

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            etStatAt.setText(formatter.format(education.getStartAt()));
            etEndAt.setText(formatter.format(education.getEndAt()));
            etDescription.setText(education.getDescription());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel:{
                finish();
                break;
            }
            case R.id.btnSave:{
                Education education = new Education();
                if(!etId.getText().toString().equals("")){
                    education.setId(Integer.parseInt(etId.getText().toString()));
                }
                SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_ACCOUNT_SESSION,MODE_PRIVATE);
                Long profileId = sharedPreferences.getLong("profileId",0);
                Profile profile  = new Profile();
                profile.setId(profileId);
                education.setProfile(profile);
                education.setSchoolName(etSchoolName.getText().toString());
                education.setDescription(etDescription.getText().toString());
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date startAt = dateFormat.parse(etStatAt.getText().toString());
                    Date endAt = dateFormat.parse(etEndAt.getText().toString());
                    education.setStartAt(startAt);
                    education.setEndAt(endAt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent i = getIntent();
                i.putExtra("education",education);
                setResult(RESULT_OK,i);
                finish();
                break;
            }
        }
    }
}