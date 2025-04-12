package com.example.task1apitransactions.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task1apitransactions.R;
import com.example.task1apitransactions.auth.TokenManager;
import com.example.task1apitransactions.ui.LoginActivity;
import com.example.task1apitransactions.ui.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            TokenManager tokenManager = new TokenManager(this);
            Intent intent;

            if (tokenManager.getToken() != null) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("from_splash", true);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}