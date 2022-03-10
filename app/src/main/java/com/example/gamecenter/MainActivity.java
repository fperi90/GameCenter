package com.example.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamecenter.PegGame.PegSolitaire;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private String userName;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*startActivity(new Intent(this, Login.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));*/
        intent = getIntent();
        userName = intent.getExtras().getString("username");
        userNameTextView = (TextView) findViewById(R.id.textViewUserName);
        userNameTextView.setText("Welcome " + userName+ "!");
        LinearLayout layout = findViewById(R.id.mainLayout);
        layout.setAlpha(0f);
        findViewById(R.id.mainLayout).animate().alpha(1f).setDuration(1500).start();
    }


    public void toPegActivity(View view) {
        Intent toPeg = new Intent(this, PegSolitaire.class);
        toPeg.putExtra("username", userName);
        startActivity(toPeg);
    }

    public void toSettingsActivity(View view) {
        Intent toSettings = new Intent(this, Settings.class);
        startActivity(toSettings);
    }

    public void to2048Activity(View view) {
        Intent to2048 = new Intent(this, Game2048.class);
        to2048.putExtra("username", userName);
        startActivity(to2048);
    }

}