package com.lkjuhkmnop.textquest.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lkjuhkmnop.textquest.R;
import com.lkjuhkmnop.textquest.tools.Tools;
import com.lkjuhkmnop.textquest.tqmanager.CloudManager;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView playBtn, addBtn, libBtn;
    private Button authBtn;
    private TextView userInfo;
    private int popupCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TextQuest);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.play_btn);
        addBtn = findViewById(R.id.add_btn);
        libBtn = findViewById(R.id.lib_btn);
        authBtn = findViewById(R.id.auth_button);
        userInfo = findViewById(R.id.user_info);

        playBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        libBtn.setOnClickListener(this);

        authBtn.setOnClickListener(v -> {
            List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    Tools.AUTH_REQUEST_CODE);
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == playBtn.getId()) {
            Tools.startGamesActivity(this, v);
        } else if (v.getId() == addBtn.getId()) {
            Tools.startQuestManageActivityToAddQuest(this, v);
        } else if (v.getId() == libBtn.getId()) {
            Tools.startLibraryActivity(this, v);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Tools.AUTH_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = Tools.authTools().setUser(FirebaseAuth.getInstance().getCurrentUser());
                String userInfoText = "Uid: " + user.getUid() + "; Prov.id: " + user.getProviderId()
                        + "\nEmail: " + user.getEmail() + "; Display name: " + user.getDisplayName();
                Log.d("AUTH", userInfoText);
                userInfo.setText(userInfoText);
                Tools.cloudManager().checkUserInUsersCollection();
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                if (response != null) {
                    Log.d("AUTH", response.getError().getMessage());
                    userInfo.setText("ERROR");
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}