package com.example.a303bus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class profileFragment extends Fragment {
    TextView name, email, phone;
    String myname, myemail;
    int myphone;
    Button logout, btnAdmin, btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = (TextView) view.findViewById(R.id.txtname);
        email = (TextView) view.findViewById(R.id.txtemail);
        phone = (TextView) view.findViewById(R.id.txtphone);
        logout = (Button) view.findViewById(R.id.logout3);
//        Hide these two buttons to not display on Fragment of profile.
        btnAdmin = requireActivity().findViewById(R.id.btnadmin);
        btnAdmin.setVisibility(View.GONE);
        btnLogin = requireActivity().findViewById(R.id.btnlogin);
        btnLogin.setVisibility(View.GONE);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        name.setText(bundle.getString("user_name"));
        email.setText(bundle.getString("user_email"));
        phone.setText(bundle.getString("user_phone"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               requireActivity().finish();
            }
        });


        return view;
    }

}