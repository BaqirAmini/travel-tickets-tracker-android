package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class singup extends AppCompatActivity {
    EditText edname, edphone, edemail, edpassword;
    Button btnsignup;
    TextView txtsignup;
    String username, email, password;
    int phone;

    String testingPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        edname = findViewById(R.id.edname);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpass);
        edphone = findViewById(R.id.edphone);
        btnsignup = findViewById(R.id.btnsignin);
        txtsignup = findViewById(R.id.txtsignup);


        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(singup.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onValidate();
//                File inStorage = getBaseContext().getExternalFilesDir(null);
//                String desPath = inStorage.getAbsolutePath() + "/bus303/database/";
//                Toast.makeText(singup.this, "Path: " + desPath, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onSignUp() {

        try {
            username = edname.getText().toString();
            email = edemail.getText().toString();
            password = edpassword.getText().toString();
            phone = Integer.parseInt(edphone.getText().toString());

            if (Build.VERSION.SDK_INT >= 23) {
                ExternalStoragePermission.verifyStoragePermissions(this);
            }
            File inStorage = getBaseContext().getExternalFilesDir(null);
            String desPath = inStorage.getAbsolutePath() + "/bus303/database/";
//            File f = new File(desPath);
            File outFile = new File(Environment.getDataDirectory(), desPath);
            SQLiteDatabase bus_db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
            ContentValues values = new ContentValues();
            values.put("user_name", username);
            values.put("email", email);
            values.put("password", password);
            values.put("phone", phone);


            bus_db.insert("users", null, values);
            Toast.makeText(getBaseContext(), "The user has been registered successfully!", Toast.LENGTH_LONG).show();
            edname.setText("");
            edemail.setText("");
            edpassword.setText("");
            edphone.setText("");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //    Validate the fields to not allow blank values.
    public void onValidate() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (edname.getText().toString().isEmpty()) {
            edname.setError("Username is required");
        }
        if (edemail.getText().toString().isEmpty()) {
            edemail.setError("Email is required");
        }
        if (edpassword.getText().toString().isEmpty()) {
            edpassword.setError("Password is required");
        }
        if (edphone.getText().toString().isEmpty()) {
            edphone.setError("Phone is required");
        }


        if (!edname.getText().toString().isEmpty() && !edemail.getText().toString().isEmpty() && !edpassword.getText().toString().isEmpty() && !edphone.getText().toString().isEmpty()) {


//            if (!email.matches(emailPattern)) {
//                edemail.setError("Email must be valid.");
//            }
//            if (edpassword.getText().toString().length() < 6) {
//                edpassword.setError("Password must be at least 6 characters.");
//            }
            onSignUp();
        }
    }


}
