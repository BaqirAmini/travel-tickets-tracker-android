package com.example.a303bus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class passenger_detail extends AppCompatActivity {

    EditText edFirsname, edlastname, edphone, edcardno, edcvv, edmmyy, edidcardno;
    RadioButton visaRadio, mastercardRaio, cashRadio;
    RadioGroup rg;
    String firstName, lastName, phone, tazkiraID, eCard, cvv, dateInput;
    Button btnSubmit;
    boolean formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_detail);
        getSupportActionBar().hide();

        btnSubmit = findViewById(R.id.btnsubmit);
        mastercardRaio = findViewById(R.id.mastercard);
        visaRadio = findViewById(R.id.visa);
        cashRadio = findViewById(R.id.cash);
        rg = findViewById(R.id.group);
        edmmyy = findViewById(R.id.edmmyy);

       /* rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (visa.isChecked()) {
                    Toast.makeText(passenger_detail.this, "visa card has been selected", Toast.LENGTH_SHORT).show();

                } else if (mastercard.isChecked()) {
                    Toast.makeText(passenger_detail.this, "mastercard card has been selected", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(passenger_detail.this, "cash has been selected", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookTicket();
            }
        });
    }

    private void onBookTicket() {
        Intent pasIntent = getIntent();
        int ticketID = pasIntent.getIntExtra("TICKET_ID", 0);
        edFirsname = findViewById(R.id.pas_first_name);
        edlastname = findViewById(R.id.pas_last_name);
        edphone = findViewById(R.id.pas_phone);
        edidcardno = findViewById(R.id.pas_tazkira_ID);
        edcardno = findViewById(R.id.pas_card_no);
        edcvv = findViewById(R.id.edcvv);
        edmmyy = findViewById(R.id.edmmyy);

//        Declare variables to store values of editTextes
        firstName = edFirsname.getText().toString();
        lastName = edlastname.getText().toString();
        phone = edphone.getText().toString();
        tazkiraID = edidcardno.getText().toString();
        eCard = edcardno.getText().toString();
        cvv = edcvv.getText().toString();
        dateInput = edmmyy.getText().toString();

        formattedDate = dateInput.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");

//        Now validate all form elements
        if (firstName.isEmpty()) {
            edFirsname.setError("First Name required.");
        } else if (firstName.length() < 3) {
            edFirsname.setError("First Name must be at least 3 characters.");
        }
        if (!lastName.isEmpty()) {
            if (lastName.length() < 3) {
                edlastname.setError("Last Name must be at least 3 characters.");
            }
        }

        if (phone.isEmpty()) {
            edphone.setError("Phone required.");
        } else if (phone.length() < 10 || phone.length() > 13) {
            edphone.setError("Phone cannot be lower than 10 or greater than 13 digits");
        }
        if (tazkiraID.isEmpty()) {
            edidcardno.setError("Tazkira ID required.");
        } else if (tazkiraID.length() != 13) {
            edidcardno.setError("Tazkira ID must contain 13 digits.");
        }
        if (eCard.isEmpty()) {
            edcardno.setError("Card number required.");
        } else if (eCard.length() != 16) {
            edcardno.setError("Card number must be 16 digits.");
        }
        if (cvv.isEmpty()) {
            edcvv.setError("CVV of the e-card required.");
        } else if (cvv.length() < 3 || cvv.length() > 4) {
            edcvv.setError("CVV can be 3 or 4 digits.");
        }
        if (dateInput.isEmpty()) {
            edmmyy.setError("The card exp date required.");
        } else if (!formattedDate) {
            edmmyy.setError("The card exp date must be MM/YY.");
        }

        if (!firstName.isEmpty()
                && !(firstName.length() < 3)
                && !phone.isEmpty()
                && !cvv.isEmpty()
                && !tazkiraID.isEmpty()
                && !eCard.isEmpty()
                && !dateInput.isEmpty()
                && !(phone.length() < 10 || phone.length() > 13)
                && (tazkiraID.length() == 13)
                && eCard.length() == 16
                && !(cvv.length() < 3 || cvv.length() > 4)
                && formattedDate) {
            Toast.makeText(this, "Now validated!", Toast.LENGTH_SHORT).show();
        }
    }
}