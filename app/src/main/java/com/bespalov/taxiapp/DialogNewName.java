package com.bespalov.taxiapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;

public class DialogNewName extends DialogFragment implements View.OnClickListener {

    final String LOG_TAG = "myLogsDialog";


    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
//                .setPositiveButton(R.string.yes, this)
//                .setNegativeButton(R.string.no, this)
//                .setMessage(R.string.message_text);
//        return adb.create();
//    }

    @Override
    public void onClick(View view) {

    }
}
