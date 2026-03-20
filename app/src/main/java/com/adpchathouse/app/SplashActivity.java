package com.adpchathouse.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView line1, line2, line3, line4, line5, line6, line7, line8, cursor;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Get all terminal lines
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        line7 = findViewById(R.id.line7);
        line8 = findViewById(R.id.line8);
        cursor = findViewById(R.id.cursor);

        // Start terminal animation sequence
        startTerminalAnimation();
    }

    private void startTerminalAnimation() {
        // Animate each line with delays (hacker typing effect)
        showLine(line1, 300);
        showLine(line2, 900);
        showLine(line3, 1500);
        showLine(line4, 2100);
        showLine(line5, 2700);
        showLine(line6, 3300);
        showLine(line7, 3900);
        showLine(line8, 4500);
        showLine(cursor, 5000);

        // Start blinking cursor
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                blinkCursor();
            }
        }, 5000);

        // Navigate to MainActivity after 6 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 6000);
    }

    private void showLine(final TextView textView, long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.VISIBLE);
                // Fade in animation
                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(150);
                textView.startAnimation(fadeIn);
            }
        }, delay);
    }

    private void blinkCursor() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        cursor.startAnimation(anim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        // Disable back button on splash screen
    }
}
