package com.example.jobfinderclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderclient.R;
import com.example.jobfinderclient.model.post.Post;

import java.util.List;

public class PostHomeAdapter extends RecyclerView.Adapter<PostHomeAdapter.PostViewHolder> {
    private OnPostListener context;
    private List<Post> posts;

    public PostHomeAdapter(List<Post> posts, Activity context) {
        this.posts = posts;
        this.context = (OnPostListener) context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View root = layoutInflater.inflate(R.layout.post_item_1,parent,false);
        return new PostViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvJobName.setText(post.getJobName());
        holder.tvCompanyName.setText(post.getCompany().getName());
        holder.tvAddress.setText(post.getAddress());
        holder.tvSalary.setText(post.getMinSalary()/1000000+" triệu - "+post.getMaxSalary()/1000000+" triệu");
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvJobName, tvCompanyName, tvAddress, tvSalary;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobName = itemView.findViewById(R.id.tvJobName);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context.onPostClicked(posts.get(getAdapterPosition()));
        }
    }

    public interface OnPostListener{
        void onPostClicked(Post post);
    }
}
