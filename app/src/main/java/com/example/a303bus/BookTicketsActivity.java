package com.example.a303bus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookTicketsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView ticketsRV;
    private TicketsBookAdapter tbAdapter;
    private List<TicketsDataModel> ticketsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_book);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ticketsRV = findViewById(R.id.rv_book_tickets);
        ticketsRV.setHasFixedSize(true);
        ticketsRV.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        ticketsList = new ArrayList<>();
        loadTicketsData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        BookTicketsActivity.this.finish();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadTicketsData() {
        Intent intentForBook = getIntent();
        String fromWhere = intentForBook.getStringExtra("FROM_WHERE");
        String toWhere = intentForBook.getStringExtra("TO_WHERE");
        String depDate = intentForBook.getStringExtra("DEP_DATE");
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                ExternalStoragePermission.verifyStoragePermissions(this);
            }
            String searchQuery = "SELECT * FROM tickets WHERE source LIKE '%" + fromWhere.trim() + "%' AND destination LIKE '%" + toWhere.trim() + "%' AND dep_date = '" + depDate.trim() + "';";
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
                    tdm.setDepDate(cursor.getString(cursor.getColumnIndexOrThrow("dep_date")));
                    tdm.setDepTime(cursor.getString(cursor.getColumnIndexOrThrow("dep_time")));
                    ticketsList.add(tdm);
                }
                while (cursor.moveToNext());
                tbAdapter = new TicketsBookAdapter(getBaseContext(), ticketsList);
                ticketsRV.setAdapter(tbAdapter);
                tbAdapter.notifyDataSetChanged();
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
        tbAdapter.onUpdateList(newList);
        return true;
    }
}