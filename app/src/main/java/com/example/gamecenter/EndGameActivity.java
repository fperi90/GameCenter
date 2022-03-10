package com.example.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class EndGameActivity extends AppCompatActivity {

    private Intent intent;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        intent = getIntent();
        userName = intent.getExtras().getString("username");
        findViewById(R.id.animationGameOver).animate().alpha(1f).setDuration(1000).start();
        new Handler().postDelayed(
                () -> findViewById(R.id.animationGameOver)
                        .animate()
                        .alpha(0f).setDuration(1000).start(), 3000
        );

        findViewById(R.id.animationGOTextView).animate().alpha(1f).setDuration(2000).setStartDelay(1000).start();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        }, 4000);

    }
}