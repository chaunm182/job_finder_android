package com.example.jobfinderclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderclient.R;
import com.example.jobfinderclient.model.profile.Education;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {

    private List<Education> educations;
    private OnEducationListener activity;

    public EducationAdapter(List<Education> educations) {
        this.educations = educations;
    }

    public EducationAdapter(List<Education> educations, Context activity) {
        this.educations = educations;
        this.activity = (OnEducationListener) activity;
    }

    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.education_item,parent,false);
        return new EducationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        Education education = educations.get(position);

        String schoolName = education.getSchoolName();
        holder.tvSchoolName.setText(schoolName);

        Date startedAt = education.getStartAt();
        Date endAt = education.getEndAt();
        if(startedAt!=null && endAt!=null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            holder.tvTime.setText(dateFormat.format(startedAt)+" - "+dateFormat.format(endAt));
        }


        String description = education.getDescription();
        holder.tvDescription.setText(description);

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onDeleteClicked(education);
            }
        });

        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onUpdateClicked(education);
            }
        });
    }

    @Override
    public int getItemCount() {
        return educations.size();
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
        notifyDataSetChanged();
    }

    public class EducationViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchoolName, tvTime, tvDescription;
        ImageView ivDelete, ivUpdate;
        public EducationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchoolName = itemView.findViewById(R.id.tvSchoolName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivDelete = itemView.findViewById(R.id.ivDeleteEducation);
            ivUpdate = itemView.findViewById(R.id.ivUpdateEducation);
        }

    }

    public interface OnEducationListener{
        void onDeleteClicked(Education educationClicked);
        void onUpdateClicked(Education educationClicked);
    }
}
