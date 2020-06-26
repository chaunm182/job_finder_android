package com.example.jobfinderclient.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<String> {
    private List<String> addressList;
    public AddressAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        addressList = objects;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
    }

}
