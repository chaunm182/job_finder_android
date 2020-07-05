package com.example.jobfinderclient.ui.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderclient.PayActivity;
import com.example.jobfinderclient.R;
import com.example.jobfinderclient.adapter.SubscribeAdapter;
import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.person.User;
import com.example.jobfinderclient.model.post.JobCategory;
import com.example.jobfinderclient.model.post.Subscriber;
import com.example.jobfinderclient.viewmodel.NotificationsViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationsFragment extends Fragment implements SubscribeAdapter.OnSubscriberListener {

    private NotificationsViewModel notificationsViewModel;
    private String roleName;
    private Long userId;

    Spinner spnJobCategory;
    ArrayAdapter<JobCategory> jobCategoriesAdapter;

    RecyclerView rvSubscribe;
    SubscribeAdapter subscribeAdapter;

    Button btnPay;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(
                Constant.SHARED_ACCOUNT_SESSION, Context.MODE_PRIVATE);
        roleName = sharedPreferences.getString("roleName","");
        userId = sharedPreferences.getLong("userId",0);
        int resources = 0;
        if(roleName.equals("ROLE_USER")){
            resources = R.layout.fragment_vip_ads;
        }
        else if(roleName.equals("ROLE_VIPUSER")){
            resources = R.layout.fragment_notifications;
        }
        View root = inflater.inflate(resources, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(roleName.equals("ROLE_VIPUSER")){
            //add controls
            spnJobCategory = view.findViewById(R.id.spnJobCategory);
            jobCategoriesAdapter = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,new ArrayList<>());
            spnJobCategory.setAdapter(jobCategoriesAdapter);
            addEvents();

            //find all categories
            notificationsViewModel.findAllJobCategories();
            notificationsViewModel.getJobCategoryLiveData().observe(getViewLifecycleOwner(),
                    new Observer<List<JobCategory>>() {
                @Override
                public void onChanged(List<JobCategory> jobCategories) {
                    jobCategoriesAdapter.add(new JobCategory((long) 0,"Chọn loại ngành nghề"));
                    jobCategoriesAdapter.addAll(jobCategories);
                    jobCategoriesAdapter.notifyDataSetChanged();
                }
            });

            //find subscriber by user id
            rvSubscribe = view.findViewById(R.id.rvSubscribe);
            subscribeAdapter = new SubscribeAdapter(new ArrayList<>(), this);
            rvSubscribe.setLayoutManager(new LinearLayoutManager(this.getContext(),
                    RecyclerView.VERTICAL,false));
            rvSubscribe.setAdapter(subscribeAdapter);
            notificationsViewModel.findSubscribersByUserId(userId);
            notificationsViewModel.getSubscribersLiveData().observe(getViewLifecycleOwner(),
                    new Observer<List<Subscriber>>() {
                @Override
                public void onChanged(List<Subscriber> subscribers) {
                    subscribeAdapter.setSubscribers(subscribers);

                }
            });

        }
        else if(roleName.equals("ROLE_USER")){
            btnPay = view.findViewById(R.id.btnPay);
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NotificationsFragment.this.getActivity(), PayActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void addEvents(){
        spnJobCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    JobCategory jobCategory = jobCategoriesAdapter.getItem(position);
                    List<Subscriber> subscribers = notificationsViewModel.getSubscribersLiveData().getValue();
                    for(Subscriber subscriber: subscribers){
                        if(jobCategory.getId()==subscriber.getJobCategory().getId()){
                            Toast.makeText(NotificationsFragment.this.getContext(),
                                    "Đã quan tâm ngành "+jobCategory.getName(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    showAddSubscriberConfirmDialog(jobCategory);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showAddSubscriberConfirmDialog(JobCategory jobCategory) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity())
                .setTitle("Xác nhận")
                .setMessage("Nhận thông báo cho ngành " +jobCategory.getName()+" khi có việc làm mới được đăng")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Subscriber subscriber = new Subscriber();
                        subscriber.setJobCategory(jobCategory);
                        User user = new User();
                        user.setId(userId);
                        subscriber.setUser(user);
                        notificationsViewModel.addSubscriber(subscriber);
                    }
                })
                .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.show();
    }


    @Override
    public void onDeleteClicked(Subscriber subscriberClicked) {
        notificationsViewModel.deleteSubscriberById(subscriberClicked.getId());
    }
}