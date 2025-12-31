package com.shubham.pgrentalapp2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.model.BookingModel;
import com.shubham.pgrentalapp2.utils.BookingRepository;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final Context context;
    private final List<BookingModel> bookingList;

    public BookingAdapter(Context context, List<BookingModel> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookingModel booking = bookingList.get(position);

        // ================= BASIC DATA =================
        holder.tvPgName.setText(booking.getPgName());
        holder.tvRent.setText("₹ " + booking.getRent() + " / Month");

        // ✅ DYNAMIC ADDRESS + CITY
        String addressText = booking.getPgAddress();
        String cityText = booking.getPgCity();

        if (addressText == null) addressText = "";
        if (cityText == null) cityText = "";

        if (!addressText.isEmpty() || !cityText.isEmpty()) {
            holder.tvAddress.setText(addressText + ", " + cityText);
        } else {
            holder.tvAddress.setText("Address not available");
        }

        // ================= STATUS =================
        holder.tvStatus.setText(booking.getStatus());

        switch (booking.getStatus().toLowerCase()) {

            case "accepted":
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
                holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
                holder.btnCancel.setVisibility(View.GONE);
                break;

            case "rejected":
                holder.tvStatus.setBackgroundColor(Color.parseColor("#FDECEA"));
                holder.tvStatus.setTextColor(Color.parseColor("#C62828"));
                holder.btnCancel.setVisibility(View.GONE);
                break;

            default: // pending
                holder.tvStatus.setBackgroundColor(Color.parseColor("#FFF3CD"));
                holder.tvStatus.setTextColor(Color.parseColor("#856404"));
                holder.btnCancel.setVisibility(View.VISIBLE);
                break;
        }

        // ================= CANCEL BOOKING =================
        holder.btnCancel.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Cancel Booking")
                    .setMessage("Are you sure you want to cancel this booking?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        boolean deleted = BookingRepository.deleteBooking(
                                context,
                                booking.getStudentEmail(),
                                booking.getPgName()
                        );

                        if (deleted) {
                            bookingList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, bookingList.size());

                            Toast.makeText(context,
                                    "Booking cancelled",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,
                                    "Failed to cancel booking",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    // ================= VIEW HOLDER =================
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPgName, tvRent, tvAddress, tvStatus;
        Button btnCancel;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPgName = itemView.findViewById(R.id.tvPgName);
            tvRent = itemView.findViewById(R.id.tvRent);
            tvAddress = itemView.findViewById(R.id.tvAddress); // ✅ IMPORTANT
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnCancel = itemView.findViewById(R.id.btnCancelBooking);
        }
    }
}
