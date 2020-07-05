package com.example.jobfinderclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.person.User;
import com.example.jobfinderclient.model.post.Post;
import com.example.jobfinderclient.model.post.RecruitmentDetail;
import com.example.jobfinderclient.ui.post.PostDialogFragment;
import com.example.jobfinderclient.viewmodel.PostViewModel;

public class PostActivity extends AppCompatActivity implements PostDialogFragment.OnPostDialogListener {

    TextView tvJobName, tvCompanyName, tvSalary, tvAmount,tvAddress, tvExperience, tvDescription,
            tvRequirement, tvBenefit, tvStatus;
    Button btnApplyJob;

    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        addControls();
        addEvents();
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
        tvStatus = findViewById(R.id.tvStatus);
        btnApplyJob = findViewById(R.id.btnApplyJob);
    }

    public void addEvents(){
        btnApplyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new PostDialogFragment(PostActivity.this);
                dialog.show(getSupportFragmentManager(),"applyDialog");
            }
        });
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

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_ACCOUNT_SESSION,MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId",0);

        postViewModel.getRecruitmentDetailLiveData().observe(this, new Observer<RecruitmentDetail>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(RecruitmentDetail recruitmentDetail) {
                btnApplyJob.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
                String status = recruitmentDetail.getStatus();
                switch (status) {
                    case "Đang chờ duyệt":
                        tvStatus.setText("Bạn đã ứng tuyển thành công. Vui lòng đợi phản hồi từ nhà tuyển dụng");
                        break;
                    case "Chấp thuận":
                        tvStatus.setText("Chúc mừng! Bạn đã qua vòng gửi CV. Vui lòng phản hồi từ nhà tuyển dụng");
                        break;
                    case "Đã tuyển dụng":
                        tvStatus.setText("Chúc mừng! Bạn đã được tuyển dụng vào công việc này");
                        tvStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case "Từ chối":
                        tvStatus.setText("Rất tiếc! CV của bạn đã bị từ chối bởi nhà tuyển dụng.");
                        tvStatus.setTextColor(Color.RED);
                        break;
                }
            }
        });
        postViewModel.findRecruitmentDetailByPostIdAndUserId(post.getId(),userId);

    }

    @Override
    public void onPositiveClicked() {
        RecruitmentDetail recruitmentDetail = new RecruitmentDetail();
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");
        recruitmentDetail.setPost(post);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_ACCOUNT_SESSION,MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId",0);
        User user = new User();
        user.setId(userId);
        recruitmentDetail.setUser(user);

        postViewModel.addRecruitmentDetail(recruitmentDetail);
    }
}