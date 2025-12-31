package com.shubham.pgrentalapp2.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.ui.home.HomeActivity;
import com.shubham.pgrentalapp2.ui.owner.OwnerDashboardActivity;
import com.shubham.pgrentalapp2.ui.student.StudentDashboardActivity;
import com.shubham.pgrentalapp2.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Lock light mode
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
        );

        setContentView(R.layout.activity_splash);

        SessionManager sessionManager = new SessionManager(this);

        new Handler().postDelayed(() -> {

            Intent intent;

            if (sessionManager.isLoggedIn()) {

                // ✅ User already logged in → go to dashboard
                if (SessionManager.ROLE_OWNER.equals(sessionManager.getUserRole())) {
                    intent = new Intent(this, OwnerDashboardActivity.class);
                } else {
                    intent = new Intent(this, StudentDashboardActivity.class);
                }

            } else {
                // ❌ Not logged in → common home
                intent = new Intent(this, HomeActivity.class);
            }

            // ✅ Clear back stack
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }, SPLASH_TIME);
    }
}
