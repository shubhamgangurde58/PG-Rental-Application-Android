package com.shubham.pgrentalapp2.ui.owner;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.adapter.PgAdapter;
import com.shubham.pgrentalapp2.model.PgModel;
import com.shubham.pgrentalapp2.utils.PgRepository;

import java.util.ArrayList;
import java.util.List;

public class OwnerAllPgsActivity extends AppCompatActivity {

    private EditText edtSearch;
    private ImageButton btnFilter;
    private RecyclerView recyclerPgList;
    private View layoutEmpty;

    private PgAdapter adapter;

    // 🔍 Filter values
    private String selectedLocation = null;
    private String addressKeyword = null;
    private Integer minRent = null;
    private Integer maxRent = null;

    private final List<PgModel> currentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_all_pgs);

        // Bind views
        edtSearch = findViewById(R.id.edtSearch);
        btnFilter = findViewById(R.id.btnFilter);
        recyclerPgList = findViewById(R.id.recyclerPgList);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        recyclerPgList.setLayoutManager(new LinearLayoutManager(this));

        // Adapter (no click for owner)
        adapter = new PgAdapter(this, currentList, null);
        recyclerPgList.setAdapter(adapter);

        // Initial load
        applySearch();

        // 🔍 Search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applySearch();
            }
        });

        // 🎛️ Filter dialog
        btnFilter.setOnClickListener(v -> showFilterDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        applySearch();
    }

    // ================= APPLY SEARCH + FILTER =================
    private void applySearch() {

        List<PgModel> result = PgRepository.searchPgs(
                this,
                edtSearch.getText().toString(), // PG name
                selectedLocation,               // City / Location
                addressKeyword,                 // Address
                minRent,
                maxRent
        );

        currentList.clear();
        currentList.addAll(result);
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    // ================= FILTER DIALOG =================
    private void showFilterDialog() {

        View view = getLayoutInflater().inflate(R.layout.dialog_filter_pg, null);

        EditText edtCity = view.findViewById(R.id.edtCity);
        EditText edtAddress = view.findViewById(R.id.edtAddress);
        EditText edtMinRent = view.findViewById(R.id.edtMinRent);
        EditText edtMaxRent = view.findViewById(R.id.edtMaxRent);
        Button btnApply = view.findViewById(R.id.btnApplyFilter);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();

        btnApply.setOnClickListener(v -> {

            selectedLocation = edtCity.getText().toString().trim();
            addressKeyword = edtAddress.getText().toString().trim();

            minRent = edtMinRent.getText().toString().isEmpty()
                    ? null
                    : Integer.parseInt(edtMinRent.getText().toString());

            maxRent = edtMaxRent.getText().toString().isEmpty()
                    ? null
                    : Integer.parseInt(edtMaxRent.getText().toString());

            applySearch();
            dialog.dismiss();
        });

        dialog.show();
    }

    // ================= EMPTY STATE =================
    private void updateEmptyState() {
        if (currentList.isEmpty()) {
            recyclerPgList.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerPgList.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
    }
}
