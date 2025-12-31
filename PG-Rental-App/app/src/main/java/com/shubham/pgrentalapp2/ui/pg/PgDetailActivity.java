package com.shubham.pgrentalapp2.ui.pg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.database.dao.OwnerDao;
import com.shubham.pgrentalapp2.model.PgModel;
import com.shubham.pgrentalapp2.ui.booking.BookPgActivity;
import com.shubham.pgrentalapp2.ui.map.NearestPgMapActivity;
import com.shubham.pgrentalapp2.utils.PgRepository;
import com.shubham.pgrentalapp2.utils.RatingRepository;

public class PgDetailActivity extends AppCompatActivity {

    // ================= UI =================
    private ImageView imgPg;
    private TextView tvPgName, tvRent, tvType,
            tvAddress, tvPgContact, tvDescription, txtRatingCount;
    private RatingBar ratingBarOverall;
    private Button btnBookPg;
    private ImageButton btnContactOwner, btnRatePg, btnViewReviews, btnViewOnMap;

    // ================= DATA =================
    private PgModel pg;
    private String ownerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_detail);

        // ================= BIND VIEWS =================
        imgPg = findViewById(R.id.imgPg);
        tvPgName = findViewById(R.id.tvPgName);
        tvRent = findViewById(R.id.tvRent);
        tvType = findViewById(R.id.tvType);
        tvAddress = findViewById(R.id.tvAddress);
        tvPgContact = findViewById(R.id.tvPgContact);
        tvDescription = findViewById(R.id.tvDescription);
        ratingBarOverall = findViewById(R.id.ratingBarOverall);
        txtRatingCount = findViewById(R.id.txtRatingCount);

        btnBookPg = findViewById(R.id.btnBookPg);
        btnContactOwner = findViewById(R.id.btnContactOwner);
        btnRatePg = findViewById(R.id.btnRatePg);
        btnViewReviews = findViewById(R.id.btnViewReviews);
        btnViewOnMap = findViewById(R.id.btnViewOnMap); // ✅ FIX

        // ================= READ pg_id =================
        int pgId = getIntent().getIntExtra("pg_id", -1);

        if (pgId == -1) {
            Toast.makeText(this, "PG detail not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ================= FETCH PG FROM DB =================
        pg = PgRepository.getPgById(this, pgId);

        if (pg == null) {
            Toast.makeText(this, "PG details not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ================= OWNER CONTACT =================
        OwnerDao ownerDao = new OwnerDao(this);
        ownerPhone = ownerDao.getOwnerPhoneByEmail(pg.getOwnerEmail());

        // ================= SET UI =================
        tvPgName.setText(pg.getName());
        tvRent.setText("₹ " + pg.getRent() + " / Month");
        tvType.setText("Type: " + pg.getType());
        tvAddress.setText("Address: " + pg.getAddress() + ", " + pg.getLocation());
        tvDescription.setText(pg.getDescription());
        tvPgContact.setText("PG Contact: " + pg.getOwnerPhone());

        // ================= IMAGE =================
        if (pg.getImageUri() != null && !pg.getImageUri().isEmpty()) {
            imgPg.setImageURI(Uri.parse(pg.getImageUri()));
        } else {
            imgPg.setImageResource(R.drawable.ic_launcher_background);
        }

        // ================= RATING =================
        float avgRating = RatingRepository.getAverageRating(this, pg.getName());
        int ratingCount = RatingRepository.getRatingCount(this, pg.getName());
        ratingBarOverall.setRating(avgRating);
        txtRatingCount.setText("(" + ratingCount + " reviews)");

        // ================= BOOK PG =================
        btnBookPg.setOnClickListener(v -> {
            Intent bookIntent = new Intent(this, BookPgActivity.class);
            bookIntent.putExtra("pg_name", pg.getName());
            bookIntent.putExtra("pg_rent", pg.getRent());
            bookIntent.putExtra("owner_email", pg.getOwnerEmail());
            bookIntent.putExtra("pg_address", pg.getAddress());
            bookIntent.putExtra("pg_city", pg.getLocation());
            startActivity(bookIntent);
        });

        // ================= CONTACT OWNER =================
        btnContactOwner.setOnClickListener(v -> showContactDialog());

        // ================= RATE PG =================
        btnRatePg.setOnClickListener(v -> {
            Intent rateIntent = new Intent(this, RatePgActivity.class);
            rateIntent.putExtra("pg_name", pg.getName());
            startActivity(rateIntent);
        });

        // ================= VIEW REVIEWS =================
        btnViewReviews.setOnClickListener(v -> {
            Intent reviewIntent = new Intent(this, ReviewListActivity.class);
            reviewIntent.putExtra("pg_name", pg.getName());
            startActivity(reviewIntent);
        });

        // ================= 🗺 VIEW PG ON MAP (FIXED) =================
        btnViewOnMap.setOnClickListener(v -> {
            Intent mapIntent = new Intent(this, NearestPgMapActivity.class);
            mapIntent.putExtra("from_pg_detail", true);
            mapIntent.putExtra("pg_id", pg.getPgId());
            mapIntent.putExtra("pg_lat", pg.getLatitude());
            mapIntent.putExtra("pg_lng", pg.getLongitude());
            startActivity(mapIntent);
        });
    }

    // ================= CONTACT DIALOG =================
    private void showContactDialog() {

        if (ownerPhone == null || ownerPhone.trim().isEmpty()) {
            Toast.makeText(this, "Owner contact not available", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Contact Owner")
                .setMessage(
                        "Owner Name: " + pg.getOwnerName() + "\n\n" +
                                "Mobile No: " + ownerPhone + "\n\n" +
                                "Email: " + pg.getOwnerEmail()
                )
                .setPositiveButton("Call Now", (d, w) -> {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + ownerPhone));
                    startActivity(callIntent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
