package com.shubham.pgrentalapp2.ui.owner;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.adapter.OwnerBookingAdapter;
import com.shubham.pgrentalapp2.model.BookingModel;
import com.shubham.pgrentalapp2.utils.BookingRepository;
import com.shubham.pgrentalapp2.utils.OwnerSessionManager;

import java.util.List;

public class OwnerBookingActivity extends AppCompatActivity {

    RecyclerView recycler;
    View layoutEmpty;

    OwnerSessionManager sessionManager;
    OwnerBookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_booking);

        recycler = findViewById(R.id.recyclerOwnerBookings);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        sessionManager = new OwnerSessionManager(this);

        loadData();
    }

    private void loadData() {

        List<BookingModel> list =
                BookingRepository.getBookingsForOwner(
                        this,
                        sessionManager.getEmail()
                );

        adapter = new OwnerBookingAdapter(this, list);
        recycler.setAdapter(adapter);

        if (list.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
