package com.example.a303bus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
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
    String firstName, lastName, phone, tazkiraID, eCard, cvv, dateInput, payMethod = "Cash";
    Button btnSubmit;
    boolean formattedDate;
    int ticketID;

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

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                edcardno = findViewById(R.id.pas_card_no);
                edcvv = findViewById(R.id.edcvv);
                edmmyy = findViewById(R.id.edmmyy);

                if (cashRadio.isChecked()) {
                    payMethod = "Cash";
                    edcardno.setVisibility(View.GONE);
                    edcvv.setVisibility(View.GONE);
                    edmmyy.setVisibility(View.GONE);
                } else {
                    payMethod = "Paid";
                    edcardno.setVisibility(View.VISIBLE);
                    edcvv.setVisibility(View.VISIBLE);
                    edmmyy.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onvalidate();
            }
        });
    }

    private void onvalidate() {
        Intent pasIntent = getIntent();
        ticketID = pasIntent.getIntExtra("TICKET_ID", 0);
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

        if (!payMethod.equals("Cash")) {
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
                    && !cvv.isEmpty()
                    && !eCard.isEmpty()
                    && !dateInput.isEmpty()
                    && eCard.length() == 16
                    && !(cvv.length() < 3 || cvv.length() > 4)
                    && formattedDate) {
//            Call this method in order to submit the data.
                onSubmit();
            }
// *********** Below else is validating non-card(for cash only) fields only *************
        } else {
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
            if (!firstName.isEmpty()
                    && !(firstName.length() < 3)
                    && !phone.isEmpty()
                    && !tazkiraID.isEmpty()
                    && !(phone.length() < 10 || phone.length() > 13)
                    && (tazkiraID.length() == 13)) {
//            Call this method in order to submit the data.
                onSubmit();
            }
        }


        /*if (firstName.isEmpty()) {
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
//            Call this method in order to submit the data.
            onSubmit();
        }*/
    }

    //    Submit all passenger values
    private void onSubmit() {
        if (Build.VERSION.SDK_INT >= 23) {
            ExternalStoragePermission.verifyStoragePermissions(this);
        }
        try {
            File inStorage = (passenger_detail.this).getExternalFilesDir(null);
            String desPath = inStorage.getAbsolutePath() + "/bus303/database/";
            SQLiteDatabase bus_db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
            ContentValues values = new ContentValues();
            values.put("first_name", firstName);
            values.put("last_name", lastName);
            values.put("phone", phone);
            values.put("tazkira_ID", tazkiraID);
            if (!payMethod.equals("Cash")) {
                values.put("card_ID", eCard);
                values.put("CVV", cvv);
                values.put("expiry_date", dateInput);
            }
            values.put("payment", payMethod);
            values.put("ticket_ID_FK", ticketID);
            bus_db.insert("passengers", null, values);
            Toast.makeText(passenger_detail.this, "Submit was success!", Toast.LENGTH_LONG).show();
            edFirsname.setText("");
            edlastname.setText("");
            edphone.setText("");
            edidcardno.setText("");
            edcardno.setText("");
            edcvv.setText("");
            edmmyy.setText("");
        } catch (Exception e) {
            Log.e("PASS_INSERT_INFO", "Could not insert passengers info " + e.getMessage());
        }
    }
}