package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

public class UpdateTicketActivity extends AppCompatActivity {

    EditText edfrom, edto, edtp, edtc;
    Button btnupdate;
    TextView txttime, txtdate;
    String formattedDate, formattedTime;
    int ticketID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ticket);
        getSupportActionBar().hide();
        btnupdate = findViewById(R.id.btnupdate2);

        edfrom = (EditText) findViewById(R.id.ticket_update_from);
        String from = edfrom.getText().toString();
        edto = (EditText) findViewById(R.id.ticket_update_to);
        String to = edto.getText().toString();
        edtp = (EditText) findViewById(R.id.ticket_update_price);
        String tp = edtp.getText().toString();
        edtc = (EditText) findViewById(R.id.ticket_update_tct_comp);
        String tc = edtc.getText().toString();

        txtdate = (TextView) findViewById(R.id.ticket_dep_date_update);

        txttime = (TextView) findViewById(R.id.ticket_dep_time_update);

//        Get ticket details from intent for updating
        Intent tctIntent = getIntent();
        ticketID = tctIntent.getIntExtra("TICKET_ID", 0);
//        Toast.makeText(this, "Ticket: " + ticketID, Toast.LENGTH_SHORT).show();
        edfrom.setText(tctIntent.getStringExtra("FROM"));
        edto.setText(tctIntent.getStringExtra("TO"));
        edtp.setText(tctIntent.getStringExtra("PRICE"));
        edtc.setText(tctIntent.getStringExtra("COMPANY"));

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(UpdateTicketActivity.this, dateset, year, month, day);
                dpd.show();
            }
        });
        txttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int min = cal.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(UpdateTicketActivity.this, timeset, hour, min, false);
                tpd.show();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromPlace = edfrom.getText().toString();
                String toPlace = edto.getText().toString();
                String tctPrice = (edtp.getText().toString());
                String priceNoVal = edtp.getText().toString();
                String tctComp = edtc.getText().toString();
                String depDate = txtdate.getText().toString();
                String depTime = txttime.getText().toString();

                if (depDate.isEmpty() || !depDate.equals(formattedDate)) {
                    txtdate.setError("Pick a date.");
                }
                if (depTime.isEmpty() || !depTime.equals(formattedTime)) {
                    txttime.setError("Pick a time.");
                }
                if (fromPlace.isEmpty()) {
                    edfrom.setError("From cannot be blank.");
                }
                if (toPlace.isEmpty()) {
                    edto.setError("From cannot be blank.");
                }
                if (tctPrice.isEmpty()) {
                    edtp.setError("Price cannot be blank.");
                } else if (Double.parseDouble(tctPrice) <= 0)
                {
                    edtp.setError("Price must be greater than zero.");
                }

                if (tctComp.isEmpty()) {
                    edtc.setError("Company cannot be blank.");
                }
                
                if (!(depDate.isEmpty() || !depDate.equals(formattedDate)) 
                        && !(depTime.isEmpty() || !depTime.equals(formattedTime)) 
                        && !fromPlace.isEmpty() 
                        && !toPlace.isEmpty() 
                        && !tctPrice.isEmpty() && 
                        !tctComp.isEmpty())
                {
                    try {
                        if (Build.VERSION.SDK_INT >= 23) {
                            ExternalStoragePermission.verifyStoragePermissions(UpdateTicketActivity.this);
                        }
                        File inStorage = getBaseContext().getExternalFilesDir(null);
                        String desPath = inStorage.getAbsolutePath() + "/bus303/database/";
                        SQLiteDatabase bus_db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
                        String query = "SELECT * FROM tickets WHERE ticket_ID = '" + ticketID + "';";
                        Cursor cursor = bus_db.rawQuery(query, null);
                        cursor.moveToFirst();
                        if (cursor.getCount() > 0)
                        {
                            ContentValues ticketUpdateValues = new ContentValues();
                            ticketUpdateValues.put("source", fromPlace);
                            ticketUpdateValues.put("destination", toPlace);
                            ticketUpdateValues.put("price", Double.parseDouble(tctPrice));
                            ticketUpdateValues.put("transport_company", tctComp);
                            ticketUpdateValues.put("dep_date", formattedDate);
                            ticketUpdateValues.put("dep_time", formattedTime);
                            bus_db.update("tickets", ticketUpdateValues, "ticket_ID = ?", new String[]{String.valueOf(ticketID)});
                            Toast.makeText(UpdateTicketActivity.this, "The ticket updated successfully!", Toast.LENGTH_LONG).show();
                            edfrom.setText("");
                            edto.setText("");
                            edtp.setText("");
                            edtc.setText("");

                          Intent intent = new Intent(UpdateTicketActivity.this, admin.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          startActivity(intent);

                        }

                    } catch (Exception e)
                    {
                        Log.e("TICKET_UPDATE", "Could not update tickets since " + e.getMessage());
                    }
                }
            }
        });

    }

    DatePickerDialog.OnDateSetListener dateset = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%02d", year, month + 1, day);
            txtdate.setText(formattedDate);
        }

    };
    TimePickerDialog.OnTimeSetListener timeset = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {
            formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, min);
            txttime.setText(formattedTime);
        }
    };
}
