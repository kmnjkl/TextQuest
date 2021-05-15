package com.lkjuhkmnop.textquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.type.TimeOfDayOrBuilder;
import com.lkjuhkmnop.textquest.tools.Tools;

public class UserManagerActivity extends AppCompatActivity {
    EditText newPass, repeatNewPass;
    Button changePassBtn, finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        newPass = findViewById(R.id.user_new_pass);
        repeatNewPass = findViewById(R.id.user_repeat_new_pass);
        changePassBtn = findViewById(R.id.user_change_pass_button);
        finishBtn = findViewById(R.id.user_finish_button);

//        Set on click listener for the "finish" button (to close the activity)
        finishBtn.setOnClickListener(v -> {
            finish();
        });

//        Password change
        changePassBtn.setOnClickListener(v -> {
            if (newPass.getText().equals("") || !newPass.getText().equals(repeatNewPass.getText())) {
                Snackbar.make(v, getText(R.string.user_enter_new_pass_warning), BaseTransientBottomBar.LENGTH_LONG).show();
            } else {
                Tools.authTools().getUser().updatePassword(newPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Snackbar.make(v, getText(R.string.user_pass_changed_successfully), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}