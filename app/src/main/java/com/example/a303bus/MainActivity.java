package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edemail, edpassword;
    Button btnlogin, btnadmin;
    String email, password;
    ImageView admin;

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
        btnadmin = findViewById(R.id.btnadmin);

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
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, admin_login.class);
                startActivity(i);
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
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = '" + email.trim() + "' AND password = '" + pwd.trim() + "'", null);
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                String user_role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                String user_phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String user_name = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                String user_email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                edemail.setText("");
                edpassword.setText("");
                Intent intent = new Intent(MainActivity.this, home.class);
                intent.putExtra("USER_NAME", user_name);
                intent.putExtra("USER_EMAIL", user_email);
                intent.putExtra("USER_PHONE", user_phone);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sorry, wrong email or password!", Toast.LENGTH_SHORT).show();

            }
        }

    }

}
