package com.example.jobfinderclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderclient.R;
import com.example.jobfinderclient.model.profile.Skill;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {
    private List<Skill> skills;

    public SkillAdapter(List<Skill> skills) {
        this.skills = skills;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.skill_item,parent,false);
        return new SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Skill skill = skills.get(position);

        holder.tvSkillName.setText(skill.getName());
        holder.tvSkillPoint.setText(skill.getPoint().toString());
        holder.tvSkillDescription.setText(skill.getDescription());
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView tvSkillName, tvSkillPoint, tvSkillDescription;
        ImageView ivUpdateSkill, ivDeleteSkill;
        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSkillName = itemView.findViewById(R.id.tvSkillName);
            tvSkillPoint = itemView.findViewById(R.id.tvSkillPoint);
            tvSkillDescription = itemView.findViewById(R.id.tvSkillDescription);
            ivUpdateSkill = itemView.findViewById(R.id.ivUpdateSkill);
            ivDeleteSkill = itemView.findViewById(R.id.ivDeleteSkill);
        }
    }
}
