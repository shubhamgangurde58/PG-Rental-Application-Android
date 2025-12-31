package com.shubham.pgrentalapp2.ui.student;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.adapter.BookingAdapter;
import com.shubham.pgrentalapp2.model.BookingModel;
import com.shubham.pgrentalapp2.utils.BookingRepository;
import com.shubham.pgrentalapp2.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BookingAdapter bookingAdapter;
    SessionManager sessionManager;
    TextView tvNoBookings;

    List<BookingModel> bookingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        recyclerView = findViewById(R.id.recyclerBookings);
        tvNoBookings = findViewById(R.id.tvNoBookings);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionManager = new SessionManager(this);

        // 🔹 Init adapter once
        bookingAdapter = new BookingAdapter(this, bookingList);
        recyclerView.setAdapter(bookingAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        bookingList.clear();
        bookingList.addAll(
                BookingRepository.getBookingsByStudent(
                        this,
                        sessionManager.getUserEmail()
                )
        );


        bookingAdapter.notifyDataSetChanged();

        tvNoBookings.setVisibility(bookingList.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(bookingList.isEmpty() ? View.GONE : View.VISIBLE);
    }

}
