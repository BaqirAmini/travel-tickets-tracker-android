package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a303bus.TicketsDataModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TicketsListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView ticketsRV;
    private TicketsAdapter adapter;
    private List<TicketsDataModel> ticketsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ticketsRV = findViewById(R.id.rv_tickets);
        ticketsRV.setHasFixedSize(true);
        ticketsRV.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        ticketsList = new ArrayList<>();
        loadTicketsData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TicketsListActivity.this.finish();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadTicketsData() {
        Intent schIntent = getIntent();
        String fromWhere = schIntent.getStringExtra("FROM_WHERE");
        String toWhere = schIntent.getStringExtra("TO_WHERE");
        String depDate = schIntent.getStringExtra("DEP_DATE");
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                ExternalStoragePermission.verifyStoragePermissions(this);
            }
            String searchQuery = "SELECT * FROM tickets WHERE source LIKE '%" + fromWhere + "%' AND destination LIKE '%" + toWhere + "%' AND dep_date = '" + depDate + "';";
            File inStorage = getBaseContext().getExternalFilesDir(null);
            String desPath = inStorage.getAbsolutePath() + "/bus303/database/";

            SQLiteDatabase db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
            Cursor cursor = db.rawQuery(searchQuery, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    TicketsDataModel tdm = new TicketsDataModel();
                    tdm.setTicketID(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("ticket_ID"))));
                    tdm.setFromWhere(cursor.getString(cursor.getColumnIndexOrThrow("source")));
                    tdm.setToWhere(cursor.getString(cursor.getColumnIndexOrThrow("destination")));
                    tdm.setTicketPrice(Double.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("price"))));
                    tdm.setTicketCompany(cursor.getString(cursor.getColumnIndexOrThrow("transport_company")));
                    tdm.setDepDate(cursor.getString(cursor.getColumnIndexOrThrow("dep_date")));
                    tdm.setDepTime(cursor.getString(cursor.getColumnIndexOrThrow("dep_time")));
                    ticketsList.add(tdm);
                }
                while (cursor.moveToNext());
                adapter = new TicketsAdapter(getBaseContext(), ticketsList);
                ticketsRV.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Sorry, no such tickets found!: ", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("TICKET_ERR", "Could not fetch tickets because " + e.getMessage());
        }


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<TicketsDataModel> newList = new ArrayList<>();
        adapter.onUpdateList(newList);
        return true;
    }
}