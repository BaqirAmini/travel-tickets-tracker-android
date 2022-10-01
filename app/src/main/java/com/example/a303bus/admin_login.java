package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

public class admin_login extends AppCompatActivity {
    EditText email, pass;
    Button login;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getSupportActionBar().hide();

        email = findViewById(R.id.adminedemail);
        pass = findViewById(R.id.adminedpass);
        login = findViewById(R.id.btnloginadmin);
        user = findViewById(R.id.user);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {
                    String adminEmail = email.getText().toString();
                    String adminPwd = pass.getText().toString();

                    if (Build.VERSION.SDK_INT >= 23) {
                        ExternalStoragePermission.verifyStoragePermissions(admin_login.this);
                    }
                    File inStorage = getBaseContext().getExternalFilesDir(null);
                    String desPath = inStorage.getAbsolutePath() + "/bus303/database/";

                    SQLiteDatabase db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
                    Cursor cursor = db.rawQuery("SELECT * FROM admins WHERE admin_email = '" + adminEmail + "' AND admin_password = '" + adminPwd + "'", null);
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        String admin_id = cursor.getString(cursor.getColumnIndexOrThrow("admin_ID"));
                        String admin_email = cursor.getString(cursor.getColumnIndexOrThrow("admin_email"));
                        String admin_name = cursor.getString(cursor.getColumnIndexOrThrow("admin_name"));
                        Intent intent = new Intent(admin_login.this, admin.class);
                        intent.putExtra("ADMIN_ID", admin_id);
                        intent.putExtra("ADMIN_EMAIL", admin_email);
                        intent.putExtra("ADMIN_NAME", admin_name);
                        startActivity(intent);

                    } else {
                        Toast.makeText(admin_login.this, "Sorry, wrong email or password!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    if (email.getText().toString().isEmpty()) {
                        email.setError("Please insert your email.");
                    }
                    if (pass.getText().toString().isEmpty()) {
                        pass.setError("Please insert your email.");
                    }
                }
            }
        });

        user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}