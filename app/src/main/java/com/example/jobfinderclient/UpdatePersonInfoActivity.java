package com.example.jobfinderclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jobfinderclient.model.person.Address;
import com.example.jobfinderclient.model.person.User;

public class UpdatePersonInfoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFirstName, etLastName, etPosition, etPhone, etAddressDetail, etStreet, etDistrict, etCity;
    Button btnCancel, btnSavePersonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person_info);
        addControls();
        showInfo();
        addEvents();
    }

    private void addEvents() {
        btnSavePersonInfo.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    public void addControls(){
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPosition = findViewById(R.id.etPosition);
        etPhone = findViewById(R.id.etPhone);
        etAddressDetail = findViewById(R.id.etAddressDetail);
        etStreet = findViewById(R.id.etStreet);
        etDistrict = findViewById(R.id.etDistrict);
        etCity = findViewById(R.id.etCity);

        btnCancel = findViewById(R.id.btnCancel);
        btnSavePersonInfo = findViewById(R.id.btnSavePersonInfo);
    }

    public void showInfo(){
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        etFirstName.setText(user.getFullName().getFirstName());
        etLastName.setText(user.getFullName().getLastName());
        etPosition.setText(user.getProfile().getPosition());
        etPhone.setText(user.getPhone());
        if(user.getAddress()!=null){
            etAddressDetail.setText(user.getAddress().getAddressDetail());
            etStreet.setText(user.getAddress().getStreet());
            etDistrict.setText(user.getAddress().getDistrict());
            etCity.setText(user.getAddress().getCity());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSavePersonInfo:{
                Intent intent = getIntent();
                User user = (User) intent.getSerializableExtra("user");
                user.getFullName().setFirstName(etFirstName.getText().toString());
                user.getFullName().setLastName(etLastName.getText().toString());
                user.getProfile().setPosition(etPosition.getText().toString());
                user.setPhone(etPhone.getText().toString());
                Address address = user.getAddress();
                if(address==null) address = new Address();
                address.setStreet(etStreet.getText().toString());
                address.setAddressDetail(etAddressDetail.getText().toString());
                address.setDistrict(etDistrict.getText().toString());
                address.setCity(etCity.getText().toString());
                user.setAddress(address);

                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                finish();
                break;
            }
            case R.id.btnCancel:{
                setResult(RESULT_CANCELED);
                finish();
                break;
            }
        }
    }
}