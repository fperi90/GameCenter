package com.example.gamecenter;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivityGameCenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_game_center);

        findViewById(R.id.appLogo).animate().alpha(1f).setDuration(1000).start();
        new Handler().postDelayed(
                () -> findViewById(R.id.appLogo)
                        .animate()
                        .alpha(0f).setDuration(1000).start(),3000
        );

        findViewById(R.id.animationView).animate().alpha(0f).setDuration(1000).setStartDelay(3000).start();

        new Handler().postDelayed(() -> {

            startActivity(new Intent(this,Login.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

        }, 4000);
    }
}