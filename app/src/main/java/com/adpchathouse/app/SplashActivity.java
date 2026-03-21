package com.adpchathouse.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView line1, line2, line3, line4, line5, line6, line7, line8, cursor;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Disable back button on splash screen
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // intentionally empty — back is disabled during splash
            }
        });

        line1  = findViewById(R.id.line1);
        line2  = findViewById(R.id.line2);
        line3  = findViewById(R.id.line3);
        line4  = findViewById(R.id.line4);
        line5  = findViewById(R.id.line5);
        line6  = findViewById(R.id.line6);
        line7  = findViewById(R.id.line7);
        line8  = findViewById(R.id.line8);
        cursor = findViewById(R.id.cursor);

        startTerminalAnimation();
    }

    private void startTerminalAnimation() {
        showLine(line1, 300);
        showLine(line2, 900);
        showLine(line3, 1500);
        showLine(line4, 2100);
        showLine(line5, 2700);
        showLine(line6, 3300);
        showLine(line7, 3900);
        showLine(line8, 4500);
        showLine(cursor, 5000);

        handler.postDelayed(this::blinkCursor, 5000);

        handler.postDelayed(() -> {
            if (isFinishing() || isDestroyed()) return;
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, 6000);
    }

    private void showLine(final TextView textView, long delay) {
        handler.postDelayed(() -> {
            textView.setVisibility(View.VISIBLE);
            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            fadeIn.setDuration(150);
            textView.startAnimation(fadeIn);
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
}
