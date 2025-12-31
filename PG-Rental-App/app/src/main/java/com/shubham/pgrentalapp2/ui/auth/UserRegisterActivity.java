package com.shubham.pgrentalapp2.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.database.dao.StudentDao;

public class UserRegisterActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtMobile, edtAddress;
    EditText edtPassword, edtConfirmPassword;
    RadioGroup rgGender;
    Button btnRegister;

    StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // 🔹 Bind views (EXACT XML IDS)
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtAddress = findViewById(R.id.edtAddress);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        rgGender = findViewById(R.id.rgGender);
        btnRegister = findViewById(R.id.btnRegister);

        studentDao = new StudentDao(this);

        btnRegister.setOnClickListener(v -> {

            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtMobile.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            // 🔹 Gender from RadioGroup
            int selectedGenderId = rgGender.getCheckedRadioButtonId();
            String gender = "";

            if (selectedGenderId != -1) {
                RadioButton rb = findViewById(selectedGenderId);
                gender = rb.getText().toString(); // Male / Female
            }

            // 🔴 Validation
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()
                    || password.isEmpty() || confirmPassword.isEmpty()) {

                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedGenderId == -1) {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔹 City not present in XML
            String city = "Not Provided";

            // 🔥 INSERT FULL DATA INTO SQLITE
            boolean success = studentDao.registerStudent(
                    name,
                    email,
                    password,
                    phone,
                    address,
                    city,
                    gender
            );

            if (success) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserLoginActivity.class));
                finish();
            } else {
                Toast.makeText(
                        this,
                        "Registration failed (Email already exists)",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
