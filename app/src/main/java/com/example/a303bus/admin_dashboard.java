package com.example.a303bus;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogout:
                Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
