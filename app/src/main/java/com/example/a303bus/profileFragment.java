package com.example.a303bus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class profileFragment extends Fragment {
    TextView name, email, phone;
    String myname, myemail;
    int myphone;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = (TextView) view.findViewById(R.id.txtname);
        email = (TextView) view.findViewById(R.id.txtemail);
        phone = (TextView) view.findViewById(R.id.txtphone);
        logout=(Button) view.findViewById(R.id.logout3);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

}