package com.shubham.pgrentalapp2.ui.auth;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.database.dao.StudentDao;
import com.shubham.pgrentalapp2.ui.student.StudentDashboardActivity;
import com.shubham.pgrentalapp2.utils.SessionManager;

public class UserLoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView txtRegister;

    SessionManager sessionManager;
    StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // 🔹 Bind views
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        sessionManager = new SessionManager(this);
        studentDao = new StudentDao(this);

        new com.shubham.pgrentalapp2.database.DatabaseHelper(this)
                .getWritableDatabase();


        // ---------------- LOGIN ----------------
        btnLogin.setOnClickListener(v -> {

            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔹 SQLite Login
            Cursor cursor = studentDao.loginStudent(email, password);

            if (cursor != null && cursor.moveToFirst()) {

                int studentId = cursor.getInt(
                        cursor.getColumnIndexOrThrow("student_id")
                );
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );
                String phone = cursor.getString(
                        cursor.getColumnIndexOrThrow("phone")
                );
                String address = cursor.getString(
                        cursor.getColumnIndexOrThrow("address")
                );
                String city = cursor.getString(
                        cursor.getColumnIndexOrThrow("city")
                );
                String gender = cursor.getString(
                        cursor.getColumnIndexOrThrow("gender")
                );

                cursor.close();

                // 🔹 Save Session
                sessionManager.createLoginSession(
                        studentId,
                        name,
                        email,
                        phone,
                        address,
                        city,
                        gender,
                        SessionManager.ROLE_STUDENT
                );

                Toast.makeText(this, "Student Login Successful", Toast.LENGTH_SHORT).show();

                // 🔹 Open Dashboard (clear back stack)
                Intent intent = new Intent(
                        UserLoginActivity.this,
                        StudentDashboardActivity.class
                );
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                );
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

        // ---------------- REGISTER ----------------
        txtRegister.setOnClickListener(v -> {
            startActivity(new Intent(
                    UserLoginActivity.this,
                    UserRegisterActivity.class
            ));
        });
    }
}
