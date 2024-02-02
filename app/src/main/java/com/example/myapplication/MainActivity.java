package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logoImageView = findViewById(R.id.logoImageView);
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1500);
        logoImageView.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new AlphaAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                logoImageView.setVisibility(ImageView.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }, SPLASH_DISPLAY_TIME);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
