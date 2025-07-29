package com.example.dashboardscreen;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView gearRecycler;
    ImageView carImage;
    ImageView road1, road2;
    float roadHeight = 176f; // match height in dp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gearRecycler = findViewById(R.id.gearRecycler);
        // Use horizontal layout for gear shift
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        gearRecycler.setLayoutManager(layoutManager);

        // Set the adapter
        GearAdapter adapter = new GearAdapter();
        gearRecycler.setAdapter(adapter);

        adapter.setSelectedPosition(1);

//
//        ImageView car = findViewById(R.id.carView);
//
//// Pulse effect
//        ObjectAnimator pulse = ObjectAnimator.ofFloat(car, "scaleX", 1f, 1.1f);
//        pulse.setDuration(500);
//        pulse.setRepeatMode(ValueAnimator.REVERSE);
//        pulse.setRepeatCount(1);
//
//        ObjectAnimator pulseY = ObjectAnimator.ofFloat(car, "scaleY", 1f, 1.1f);
//        pulseY.setDuration(500);
//        pulseY.setRepeatMode(ValueAnimator.REVERSE);
//        pulseY.setRepeatCount(1);
//
//// Forward movement + fade
//        ObjectAnimator move = ObjectAnimator.ofFloat(car, "translationY", 0, -400f);
//        move.setDuration(1000);
//        ObjectAnimator fade = ObjectAnimator.ofFloat(car, "alpha", 1f, 0f);
//        fade.setDuration(1000);
//
//// Play pulse first, then move & fade
//        AnimatorSet set = new AnimatorSet();
//        set.play(pulse).with(pulseY);
//        set.play(move).with(fade).after(pulse);
//        set.start();

//        ImageView road = findViewById(R.id.roadView);
//
//// Move the road background up in a loop
//        ObjectAnimator roadAnim = ObjectAnimator.ofFloat(road, "translationY", 0f, -400f);
//        roadAnim.setDuration(2000);
//        roadAnim.setRepeatCount(ValueAnimator.INFINITE);
//        roadAnim.setRepeatMode(ValueAnimator.RESTART);
//        roadAnim.start();
        road1 = findViewById(R.id.road1);
        road2 = findViewById(R.id.road2);
        animateRoad();
    }
    private void animateRoad() {
        final Handler handler = new Handler();
        final long frameRate = 10; // ms
        final float speed = 1.5f; // pixels per frame

        handler.post(new Runnable() {
            float y1 = 0;
            float y2 = roadHeight;

            @Override
            public void run() {
                y1 += speed;
                y2 += speed;

                if (y1 >= roadHeight) y1 = -roadHeight;
                if (y2 >= roadHeight) y2 = -roadHeight;

                road1.setTranslationY(y1);
                road2.setTranslationY(y2);

                handler.postDelayed(this, frameRate);
            }
        });
    }
}