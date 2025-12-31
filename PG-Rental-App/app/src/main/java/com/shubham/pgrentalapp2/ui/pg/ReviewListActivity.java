package com.shubham.pgrentalapp2.ui.pg;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.adapter.ReviewAdapter;
import com.shubham.pgrentalapp2.model.ReviewModel;
import com.shubham.pgrentalapp2.utils.ReviewRepository;

import java.util.List;

public class ReviewListActivity extends AppCompatActivity {

    RecyclerView recyclerReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        recyclerReviews = findViewById(R.id.recyclerReviews);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(this));

        // 🔹 Get PG name safely
        String pgName = getIntent().getStringExtra("pg_name");

        if (pgName == null) {
            Toast.makeText(this, "PG not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔹 Load reviews
        List<ReviewModel> reviews =
                ReviewRepository.getReviews(this, pgName);

        if (reviews.isEmpty()) {
            Toast.makeText(this, "No reviews yet", Toast.LENGTH_SHORT).show();
        }

        recyclerReviews.setAdapter(new ReviewAdapter(reviews));
    }
}
