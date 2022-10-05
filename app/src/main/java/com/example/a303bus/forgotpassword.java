package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class forgotpassword extends AppCompatActivity {
    TextView back;
    EditText edname, edemail, edpassword;
    Button btncreate;
    String userName, userEmail, userNewPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        getSupportActionBar().hide();
        back = findViewById(R.id.txtsignup);
        edname = findViewById(R.id.edname);

        edemail = findViewById(R.id.edemail);

        edpassword = findViewById(R.id.edpass);

        btncreate = findViewById(R.id.btncreate);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(forgotpassword.this, MainActivity.class);
                startActivity(i);
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateNewPassword();
            }
        });
    }

    private void onCreateNewPassword() {
        userName = edname.getText().toString();
        userEmail = edemail.getText().toString();
        userNewPwd = edpassword.getText().toString();
//        if (!userName.isEmpty() && !userEmail.isEmpty() && !userNewPwd.isEmpty()) {
            if (userName.isEmpty())
            {
                edname.setError("Username required.");
            }
            if (userEmail.isEmpty())
            {
                edemail.setError("Email required.");
            }

            if (userNewPwd.isEmpty())
            {
                edpassword.setError("Password required.");
            } else if (userNewPwd.length() < 6)
            {
                edpassword.setError("Password must be at least 6 characters.");
            }

            if (!userName.isEmpty() && !userEmail.isEmpty() && !userNewPwd.isEmpty() && !(userNewPwd.length() < 6))

            {
                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        ExternalStoragePermission.verifyStoragePermissions(this);
                    }
                    File inStorage = getBaseContext().getExternalFilesDir(null);
                    String desPath = inStorage.getAbsolutePath() + "/bus303/database/";

                    SQLiteDatabase bus303_db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
                    Cursor cursor = bus303_db.rawQuery("SELECT * FROM users WHERE user_name = '" + userName.trim() + "' AND email = '" + userEmail.trim() + "'", null);
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        ContentValues values = new ContentValues();
                        values.put("password", userNewPwd);
                        bus303_db.update("users", values, "email = ?", new String[]{userEmail});
                        edname.setText("");
                        edemail.setText("");
                        edpassword.setText("");
                        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(forgotpassword.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Incorrect username or email.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e)
                {
                    Log.e("PASSWORD_RESET", "Cannot reset password since ");
                }
            }






    }
}