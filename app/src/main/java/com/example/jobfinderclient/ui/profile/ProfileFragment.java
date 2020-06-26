package com.example.jobfinderclient.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobfinderclient.UpdatePersonInfoActivity;
import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.person.User;
import com.example.jobfinderclient.viewmodel.ProfileViewModel;
import com.example.jobfinderclient.R;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel profileViewModel;

    TextView tvFullName, tvPosition, tvEmail, tvPhone, tvAddress, tvRoleName;
    ImageView ivCreatePersonInfo;

    private User currentUser;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root =  inflater.inflate(R.layout.fragment_profile, container, false);
        addControls(root);
        addEvents();
        showUserInfo();
        return root;
    }

    public void addControls(View view){
        tvFullName = view.findViewById(R.id.tvFullName);
        tvPosition = view.findViewById(R.id.tvPosition);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvRoleName = view.findViewById(R.id.tvRoleName);

        ivCreatePersonInfo = view.findViewById(R.id.ivCreatePersonInfo);
    }

    public void addEvents(){
        ivCreatePersonInfo.setOnClickListener(this);
    }

    public void showUserInfo(){
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(
                Constant.SHARED_ACCOUNT_SESSION, Context.MODE_PRIVATE);
        Long accountId = sharedPreferences.getLong("id",0);
        profileViewModel.findByAccountId(accountId);
        profileViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUser = user;
                String fullName = user.getFullName().getFirstName()+" "+user.getFullName().getLastName();
                tvFullName.setText(fullName);

                String roleName = user.getAccount().getRole().getName();
                if(roleName.equals("ROLE_VIPUSER")){
                    tvRoleName.setVisibility(View.VISIBLE);
                }

                String position = user.getProfile().getPosition();
                tvPosition.setText(position);

                String email = user.getAccount().getEmail();
                tvEmail.setText(email);

                String phone = user.getPhone();
                tvPhone.setText(phone);


                StringBuilder address = new StringBuilder("");
                if(user.getAddress()!=null){
                    address.append(user.getAddress().getAddressDetail()).append(", ")
                            .append(user.getAddress().getStreet()).append(", ")
                            .append(user.getAddress().getDistrict()).append(", ")
                            .append(user.getAddress().getCity());
                }

                tvAddress.setText(address.toString());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCreatePersonInfo:{
                Intent intent = new Intent(this.getActivity(), UpdatePersonInfoActivity.class);
                intent.putExtra("user",currentUser);
                this.startActivityForResult(intent, Constant.REQUEST_CODE_UPDATE_PERSON_INFO);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Constant.REQUEST_CODE_UPDATE_PERSON_INFO &&
                resultCode == this.getActivity().RESULT_OK){
            User user = (User) data.getSerializableExtra("user");
            profileViewModel.updateUser(user);
            Toast.makeText(this.getContext(),"Lưu thông tin thành công",Toast.LENGTH_SHORT).show();
        }

    }
}