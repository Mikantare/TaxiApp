package com.bespalov.taxiapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class DialogNewName extends DialogFragment implements View.OnClickListener {

    private EditNameDialogListener listener;

    private EditText editTextViewFolderDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.fragment_simple_dialog_edit_name, null);
        editTextViewFolderDialog = v.findViewById(R.id.editTextViewFolderDialog);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.btnNo).setOnClickListener(this);
        listener = (EditNameDialogListener)getActivity();
        return v;
    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }


    public void onClick(View v) {
       String newName = editTextViewFolderDialog.getText().toString().trim();
       if (((Button) v).getText().equals("yes")) {
           listener.onFinishEditDialog(newName);
       }
               dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
