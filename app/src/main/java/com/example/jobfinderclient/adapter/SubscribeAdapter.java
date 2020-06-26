package com.example.jobfinderclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobfinderclient.R;
import com.example.jobfinderclient.model.post.Subscriber;

import java.util.List;

public class SubscribeAdapter extends RecyclerView.Adapter<SubscribeAdapter.SubscribeViewHolder> {

    private List<Subscriber> subscribers;

    public SubscribeAdapter(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    @NonNull
    @Override
    public SubscribeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SubscribeViewHolder(layoutInflater.inflate(R.layout.subscribe_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeViewHolder holder, int position) {
        Subscriber subscriber = subscribers.get(position);
        holder.tvJobCategoryName.setText(subscriber.getJobCategory().getName());
        holder.tvSubscribeAt.setText(subscriber.getCreatedAt().toString());
    }

    @Override
    public int getItemCount() {
        return subscribers.size();
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
        notifyDataSetChanged();
    }

    public class SubscribeViewHolder extends RecyclerView.ViewHolder{
        TextView tvJobCategoryName, tvSubscribeAt;

        public SubscribeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobCategoryName = itemView.findViewById(R.id.tvJobCategoryName);
            tvSubscribeAt = itemView.findViewById(R.id.tvSubscribeAt);
        }
    }
}
