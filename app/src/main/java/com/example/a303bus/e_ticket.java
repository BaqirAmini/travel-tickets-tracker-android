package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class e_ticket extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eticket);

//        Access widgets...
        TextView txtComp = findViewById(R.id.tc);
        TextView txtDateTime = findViewById(R.id.date);
        TextView txtFrom = findViewById(R.id.from);
        TextView txtTo = findViewById(R.id.to);
        TextView txtPNL = findViewById(R.id.pname);
        TextView txtTctNO = findViewById(R.id.tno);

//   Fetch values from intent to generate tickets.
        Intent intent = getIntent();
        txtComp.setText(intent.getStringExtra("TCT_COMP"));
        txtDateTime.setText(intent.getStringExtra("PDATE") + " At " + intent.getStringExtra("PTIME"));
        txtFrom.setText(intent.getStringExtra("PFROM"));
        txtTo.setText(intent.getStringExtra("PTO"));
        txtPNL.setText(intent.getStringExtra("FIRST_NAME") + " " + intent.getStringExtra("LAST_NAME"));
        txtTctNO.setText(intent.getStringExtra("TICKET_NO"));

    }
}