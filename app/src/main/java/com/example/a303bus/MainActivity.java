package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    EditText edemail, edpassword;
    Button btnlogin;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpass);

        TextView signup = findViewById(R.id.signup);
        TextView txtforgot = findViewById(R.id.forgotpss);


        btnlogin = findViewById(R.id.btnlogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, singup.class);
                startActivity(i);
            }
        });

        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, forgotpassword.class);
                startActivity(i);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onValidate();
            }
        });

    }

    //    Validate to not allow click the log in button without values.
    public void onValidate() {
        if (edemail.getText().toString().isEmpty()) {
            edemail.setError("Email field cannot be blank.");

        }

        if (edpassword.getText().toString().isEmpty()) {
            edpassword.setError("Password field cannot be blank.");
        }

//        Check to see
        if (!edemail.getText().toString().isEmpty() && !edpassword.getText().toString().isEmpty()) {
            String email = edemail.getText().toString();
            String pwd = edpassword.getText().toString();

            if (Build.VERSION.SDK_INT >= 23) {
                ExternalStoragePermission.verifyStoragePermissions(this);
            }
            File inStorage = getBaseContext().getExternalFilesDir(null);
            String desPath = inStorage.getAbsolutePath() + "/bus303/database/";

            SQLiteDatabase db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + pwd + "'", null);
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                String user_role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                Intent intent = new Intent(MainActivity.this, profileFragment.class);
                intent.putExtra("user_role", user_role);
            } else {
                Toast.makeText(this, "Sorry, wrong email or password!", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
