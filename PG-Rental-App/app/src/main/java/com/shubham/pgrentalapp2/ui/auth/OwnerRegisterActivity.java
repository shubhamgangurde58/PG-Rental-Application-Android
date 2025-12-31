package com.shubham.pgrentalapp2.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.database.dao.OwnerDao;
import com.shubham.pgrentalapp2.ui.owner.OwnerDashboardActivity;
import com.shubham.pgrentalapp2.utils.OwnerSessionManager;

public class OwnerRegisterActivity extends AppCompatActivity {

    // UI
    private EditText edtOwnerName, edtPgName, edtEmail, edtMobile,
            edtAddress, edtCity, edtPassword, edtConfirmPassword;
    private Button btnRegister;

    // DAO + Session
    private OwnerDao ownerDao;
    private OwnerSessionManager ownerSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        // Bind views
        edtOwnerName = findViewById(R.id.edtOwnerName);
        edtPgName = findViewById(R.id.edtPgName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtAddress = findViewById(R.id.edtAddress);
        edtCity = findViewById(R.id.edtCity);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        ownerDao = new OwnerDao(this);
        ownerSessionManager = new OwnerSessionManager(this);

        btnRegister.setOnClickListener(v -> registerOwner());
    }

    private void registerOwner() {

        String ownerName = edtOwnerName.getText().toString().trim();
        String pgName = edtPgName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String mobile = edtMobile.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String city = edtCity.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // 🔴 Validation
        if (TextUtils.isEmpty(ownerName)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword)) {

            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔥 INSERT INTO SQLITE
        boolean success = ownerDao.registerOwner(
                ownerName,
                pgName,
                email,
                password,
                mobile,
                address,
                city
        );

        if (!success) {
            Toast.makeText(
                    this,
                    "Registration failed (Email already exists)",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // ✅ GET OWNER ID
        int ownerId = ownerDao.getOwnerIdByEmail(email);

        // ✅ CREATE LOGIN SESSION (BASIC)
        ownerSessionManager.login(
                ownerId,
                ownerName,
                email,
                mobile,
                password
        );

        // ✅ SAVE FULL PROFILE INTO SESSION (🔥 MOST IMPORTANT FIX)
        ownerSessionManager.saveOwnerProfile(
                ownerName,
                pgName,
                email,
                mobile,
                address,
                city
        );

        Toast.makeText(this, "Owner registered successfully", Toast.LENGTH_SHORT).show();

        // ✅ GO TO DASHBOARD
        Intent intent = new Intent(this, OwnerDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
