package com.example.jobfinderclient.ui.jobsearch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jobfinderclient.R;
import com.example.jobfinderclient.adapter.AddressAdapter;
import com.example.jobfinderclient.adapter.JobCategoryAdapter;
import com.example.jobfinderclient.model.post.JobCategory;
import com.example.jobfinderclient.viewmodel.FilterDialogViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilterDialogFragment extends DialogFragment {
    private View view;
    private FilterDialogViewModel filterDialogViewModel;

    private FilterDialogListener  filterDialogListener;

    private String filterAddress, filterJobCategoryId;

    Spinner spnAddress, spJobCategory;
    AddressAdapter addressAdapter;
    JobCategoryAdapter jobCategoryAdapter;

    public FilterDialogFragment() {
    }

    public FilterDialogFragment(FilterDialogListener filterDialogListener) {
        this.filterDialogListener =  filterDialogListener;
    }

    public FilterDialogFragment(FilterDialogListener filterDialogListener, String filterAddress, String filterJobCategoryId) {
        this.filterDialogListener = filterDialogListener;
        this.filterAddress = filterAddress;
        this.filterJobCategoryId = filterJobCategoryId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = getLayoutInflater().inflate(R.layout.filter_dialog,null);
        addControls(view);
        builder.setView(view)
                .setPositiveButton(R.string.positive_button_filter_dialog,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String address= (String) spnAddress.getSelectedItem();
                        JobCategory jobCategory = (JobCategory) spJobCategory.getSelectedItem();
                        filterDialogListener.onPositiveClicked(address,jobCategory);
                    }
                })
                .setNegativeButton(R.string.negative_button_filter_dialog,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        filterDialogViewModel = ViewModelProviders.of(this).get(FilterDialogViewModel.class);
        filterDialogViewModel.findAllAddress();
        filterDialogViewModel.getAddressListMutableLiveData().observe(getViewLifecycleOwner(),
                new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        addressAdapter.add("Tất cả địa điểm");
                        addressAdapter.addAll(strings);
                        addressAdapter.notifyDataSetChanged();
                        for(int i=0;i<strings.size();i++){
                            if(filterAddress.equals("all")) break;
                            else if(filterAddress.equals(strings.get(i))) {
                                spnAddress.setSelection(i+1);
                                break;
                            }
                        }
                    }
                });

        filterDialogViewModel.findAllJobCategories();
        filterDialogViewModel.getJobCategoriesListMutableLiveData().observe(getViewLifecycleOwner(),
                new Observer<List<JobCategory>>() {
                    @Override
                    public void onChanged(List<JobCategory> jobCategories) {
                        jobCategoryAdapter.add(new JobCategory((long) 0,"Tất cả ngành nghề"));
                        jobCategoryAdapter.addAll(jobCategories);
                        jobCategoryAdapter.notifyDataSetChanged();
                        for(int i=0;i<jobCategories.size();i++){
                            if(Integer.parseInt(filterJobCategoryId)==jobCategories.get(i).getId()){
                                spJobCategory.setSelection(i+1);
                                break;
                            }
                        }
                    }
                });
    }

    public void addControls(View view){
        spnAddress = view.findViewById(R.id.spAddress);
        addressAdapter = new AddressAdapter(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,new ArrayList<>());
        spnAddress.setAdapter(addressAdapter);

        spJobCategory = view.findViewById(R.id.spJobCategory);
        jobCategoryAdapter = new JobCategoryAdapter(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,new ArrayList<>());
        spJobCategory.setAdapter(jobCategoryAdapter);
    }

    public interface FilterDialogListener{
        void onPositiveClicked(String address, JobCategory jobCategory);
    }
}
