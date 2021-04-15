package com.lkjuhkmnop.textquest.questmanageactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lkjuhkmnop.textquest.R;

public class CharPropDialog extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface CharPropDialogListener {
        public void onCharPropDialogPositiveClick(String charPropAddName);
        public void onCharPropDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    CharPropDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (CharPropDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.char_prop_add_dialog, null);

        builder.setTitle(R.string.char_prop_add_dialog_title);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.char_prop_add_ok, (dialog, which) -> {
            EditText charPropAddNameET = (EditText) dialogView.findViewById(R.id.char_prop_add_name);
            listener.onCharPropDialogPositiveClick(charPropAddNameET.getText().toString());
        })
                .setNegativeButton(R.string.char_prop_add_cancel, (dialog, which) -> listener.onCharPropDialogNegativeClick());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialogView.findViewById(R.id.char_prop_add_name).requestFocus();

        return dialog;
    }
}
