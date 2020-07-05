package com.example.jobfinderclient.ui.post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.jobfinderclient.R;

public class PostDialogFragment extends DialogFragment {

    private OnPostDialogListener onPostDialogListener;

    public PostDialogFragment(OnPostDialogListener onPostDialogListener) {
        this.onPostDialogListener = onPostDialogListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Xác nhận")
                .setMessage("Bạn chắc chắn muốn ứng tuyển vào công việc này?")
                .setPositiveButton(R.string.positive_button_filter_dialog,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onPostDialogListener.onPositiveClicked();
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

    public interface OnPostDialogListener{
        void onPositiveClicked();
    }
}
