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

/**
 * SplashActivity — displays an animated terminal boot sequence before launching {@link MainActivity}.
 *
 * <p>Shows 8 lines of text appearing sequentially with a fade-in effect, followed by a
 * blinking cursor animation. After {@link #TOTAL_DURATION_MS} milliseconds the app
 * transitions to {@link MainActivity} with a fade transition.
 *
 * <p>Back navigation is intentionally disabled during the splash screen.
 *
 * @author Atharv Pawar
 * @version 1.0.0
 */
public class SplashActivity extends AppCompatActivity {

    /** Delay between each terminal line appearing, in milliseconds. */
    private static final long LINE_INTERVAL_MS = 600;

    /** Duration of the fade-in animation for each line, in milliseconds. */
    private static final long FADE_IN_MS = 150;

    /** Total splash screen duration before launching MainActivity, in milliseconds. */
    private static final long TOTAL_DURATION_MS = 6000;

    private TextView line1, line2, line3, line4, line5, line6, line7, line8, cursor;
    private final Handler handler = new Handler(Looper.getMainLooper());

    /**
     * Called when the activity is first created.
     * Inflates the splash layout, disables back navigation,
     * binds views, and starts the terminal animation sequence.
     *
     * @param savedInstanceState previously saved instance state, or null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Disable back button during splash to prevent premature exit
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

    /**
     * Schedules each terminal line to appear sequentially using {@link #LINE_INTERVAL_MS} spacing.
     * After all lines are shown, starts the cursor blink and schedules the transition
     * to {@link MainActivity} after {@link #TOTAL_DURATION_MS}.
     */
    private void startTerminalAnimation() {
        showLine(line1, LINE_INTERVAL_MS);
        showLine(line2, LINE_INTERVAL_MS * 2);
        showLine(line3, LINE_INTERVAL_MS * 3);
        showLine(line4, LINE_INTERVAL_MS * 4);
        showLine(line5, LINE_INTERVAL_MS * 5);
        showLine(line6, LINE_INTERVAL_MS * 6);
        showLine(line7, LINE_INTERVAL_MS * 7);
        showLine(line8, LINE_INTERVAL_MS * 8);
        showLine(cursor, LINE_INTERVAL_MS * 8 + 500);

        handler.postDelayed(this::blinkCursor, LINE_INTERVAL_MS * 8 + 500);

        handler.postDelayed(() -> {
            if (isFinishing() || isDestroyed()) return;
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, TOTAL_DURATION_MS);
    }

    /**
     * Makes a TextView visible after a specified delay with a fade-in animation.
     *
     * @param textView the view to show
     * @param delay    delay in milliseconds before the view becomes visible
     */
    private void showLine(final TextView textView, long delay) {
        handler.postDelayed(() -> {
            textView.setVisibility(View.VISIBLE);
            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            fadeIn.setDuration(FADE_IN_MS);
            textView.startAnimation(fadeIn);
        }, delay);
    }

    /**
     * Starts an infinite blinking animation on the cursor TextView.
     * Alternates between invisible and visible with a 500ms cycle.
     */
    private void blinkCursor() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        cursor.startAnimation(anim);
    }

    /**
     * Cancels all pending handler callbacks to prevent memory leaks
     * when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
