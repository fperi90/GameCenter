package com.example.gamecenter;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private DBHelper helper;
    private EditText textUser;
    private EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helper = new DBHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
/*        if (database != null) {
            Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "BASE DE DATOS erronea", Toast.LENGTH_SHORT).show();

        }*/
        LinearLayout layout = findViewById(R.id.loginLayout);
        layout.setAlpha(0f);
        findViewById(R.id.loginLayout).animate().alpha(1f).setDuration(1500).start();
    }

    public void login(View view) {
        textUser = (EditText) findViewById(R.id.editTextUser);
        textPassword = (EditText) findViewById(R.id.editTextPassword);
        String username = textUser.getText().toString();
        String password = textPassword.getText().toString();
        if (helper.checkUser(username, password)) {
            String message = helper.getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Intent toMainActivity = new Intent(this, MainActivity.class);
            toMainActivity.putExtra("username", username);
            startActivity(toMainActivity);
        } else {
            String message = helper.getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view) {
        Intent toRegisterActivity = new Intent(this, Register.class);
        /*toRegisterActivity.putExtra("BDHelper", (Parcelable) helper);*/
        startActivity(toRegisterActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
}