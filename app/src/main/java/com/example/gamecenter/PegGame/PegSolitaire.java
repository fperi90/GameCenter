package com.example.gamecenter.PegGame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamecenter.R;

public class PegSolitaire extends AppCompatActivity {

    private Intent intent;
    private String userName;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peg_solitaire);
        intent = getIntent();
        userName = intent.getExtras().getString("username");
        Tablero tablero = new Tablero(this,userName);
        userNameTextView = (TextView) findViewById(R.id.gameUserPeg);
        userNameTextView.setText(userName);

    }
}