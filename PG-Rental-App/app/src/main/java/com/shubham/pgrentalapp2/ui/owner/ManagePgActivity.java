package com.shubham.pgrentalapp2.ui.owner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.shubham.pgrentalapp2.R;
import com.shubham.pgrentalapp2.model.PgModel;
import com.shubham.pgrentalapp2.utils.OwnerSessionManager;
import com.shubham.pgrentalapp2.utils.PgRepository;

import java.util.List;

public class ManagePgActivity extends AppCompatActivity {

    private Button btnAddPg, btnUpdatePg, btnDeletePg;
    private OwnerSessionManager sessionManager;
    private List<PgModel> ownerPgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pg);

        btnAddPg = findViewById(R.id.btnAddPg);
        btnUpdatePg = findViewById(R.id.btnUpdatePg);
        btnDeletePg = findViewById(R.id.btnDeletePg);

        sessionManager = new OwnerSessionManager(this);

        loadOwnerPgs();

        // ================= ADD PG =================
        btnAddPg.setOnClickListener(v -> {
            if (!ownerPgs.isEmpty()) {
                Toast.makeText(
                        this,
                        "You can add only one PG per account",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            startActivity(new Intent(this, AddPgActivity.class));
        });

        // ================= UPDATE PG =================
        btnUpdatePg.setOnClickListener(v -> {
            if (ownerPgs.isEmpty()) {
                Toast.makeText(
                        this,
                        "No PG available to update",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            PgModel pg = ownerPgs.get(0);

            Intent intent = new Intent(this, UpdatePgActivity.class);
            intent.putExtra("pg_id", pg.getPgId());
            startActivity(intent);
        });

        // ================= DELETE PG =================
        btnDeletePg.setOnClickListener(v -> {
            if (ownerPgs.isEmpty()) {
                Toast.makeText(
                        this,
                        "No PG available to delete",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            PgModel pg = ownerPgs.get(0);

            new AlertDialog.Builder(this)
                    .setTitle("Delete PG")
                    .setMessage("Are you sure you want to delete this PG?")
                    .setPositiveButton("Yes", (d, w) -> {

                        // 🔥 DELETE FROM DB
                        PgRepository.deletePg(this, pg.getPgId());

                        Toast.makeText(
                                this,
                                "PG deleted successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        // 🔥 REFRESH LOCAL LIST
                        ownerPgs.clear();

                        // 🔥 RETURN TO DASHBOARD CLEANLY
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    // ================= LOAD OWNER PGs =================
    private void loadOwnerPgs() {
        ownerPgs = PgRepository.getPgsByOwner(
                this,
                sessionManager.getEmail()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOwnerPgs();
    }
}
