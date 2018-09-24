package com.keelanb.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.keelanb.stormy.R;

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        int errorCode = getArguments().getInt("errorCode");

        // If error code is 0, response from api was unsuccessful
        // If error code is 1, network unavailable
        if (errorCode == 0) {
            builder.setTitle(R.string.error_title)
                    .setMessage(R.string.error_message)
                    .setPositiveButton(R.string.error_button_ok, null);
        } else {
            builder.setTitle(R.string.error_title)
                    .setMessage(R.string.network_error_message)
                    .setPositiveButton(R.string.error_button_ok, null);
        }

        return builder.create();
    }
}
