package com.example.a303bus;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;


public class admin_dashboard extends Fragment implements View.OnClickListener {

    Button btnlogout;
    TextView sales, tickets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);
        btnlogout = (Button) view.findViewById(R.id.btnlogout);
        sales = (TextView) view.findViewById(R.id.money);
        tickets = (TextView) view.findViewById(R.id.tickets);
        btnlogout.setOnClickListener(this);

        try {

            File inStorage = requireActivity().getExternalFilesDir(null);
            String desPath = inStorage.getAbsolutePath() + "/bus303/database/";

            SQLiteDatabase db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
            Cursor tCount = db.rawQuery("SELECT COUNT(ticket_ID) FROM tickets", null);
            Cursor priceSum = db.rawQuery("SELECT SUM(price) FROM tickets", null);
            tCount.moveToFirst();
            priceSum.moveToFirst();
            tickets.setText(String.valueOf(tCount.getInt(0)));
            sales.setText(String.valueOf(priceSum.getInt(0)).concat(" AFN"));
            tCount.close();

        } catch (Exception e) {
            Log.e("TICKET_COUNT", "Could not count tickets since " + e.getMessage());
        }
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogout:
                Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireActivity(), spalsh.class);
                startActivity(intent);
                requireActivity().finish();
                break;
            default:
                break;
        }
    }
}
