package com.shubham.pgrentalapp2.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.model.BookingModel;
import com.shubham.pgrentalapp2.utils.BookingRepository;
import com.shubham.pgrentalapp2.utils.SessionManager;

public class BookPgActivity extends AppCompatActivity {

    private TextView tvPgName, tvRent, tvStatus;
    private Button btnConfirmBooking;

    private SessionManager sessionManager;

    // ================= PG DATA =================
    private String pgName, rent, ownerEmail, pgAddress, pgCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_pg);

        // ================= BIND VIEWS =================
        tvPgName = findViewById(R.id.tvPgName);
        tvRent = findViewById(R.id.tvRent);
        tvStatus = findViewById(R.id.tvStatus);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        sessionManager = new SessionManager(this);

        // ================= GET INTENT DATA =================
        Intent intent = getIntent();

        pgName = intent.getStringExtra("pg_name");
        rent = intent.getStringExtra("pg_rent");
        ownerEmail = intent.getStringExtra("owner_email");
        pgAddress = intent.getStringExtra("pg_address"); // ✅ dynamic
        pgCity = intent.getStringExtra("pg_city");       // ✅ dynamic

        // ================= VALIDATION =================
        if (pgName == null || ownerEmail == null) {
            Toast.makeText(this, "Invalid PG data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (pgAddress == null) pgAddress = "";
        if (pgCity == null) pgCity = "";

        // ================= SET UI =================
        tvPgName.setText(pgName);
        tvRent.setText("₹ " + rent + " / Month");
        tvStatus.setText("Status: PENDING");

        // ================= CONFIRM BOOKING =================
        btnConfirmBooking.setOnClickListener(v -> {

            String studentEmail = sessionManager.getUserEmail();

            // 🔁 DUPLICATE CHECK
            if (BookingRepository.hasBooking(this, studentEmail, pgName)) {
                Toast.makeText(
                        this,
                        "You have already sent a booking request for this PG",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            // ✅ CREATE FULL BOOKING MODEL
            BookingModel booking = new BookingModel(
                    pgName,
                    rent,
                    pgAddress,                 // ✅ address
                    pgCity,                    // ✅ city
                    sessionManager.getUserName(),
                    studentEmail,
                    sessionManager.getUserPhone(),
                    ownerEmail,
                    "PENDING"
            );

            BookingRepository.addBooking(this, booking);

            Toast.makeText(
                    this,
                    "Booking request sent successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}
