package com.example.dashboardscreen;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.hardware.lights.Light;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {
    int vhallighting_status=1,vhalright_light_status=0,vhalleft_light_status =1,vhalbatterylevel=15;
    RecyclerView gearRecycler;

    TextView charge_txt;

    VideoView videoView;
    ImageView road1, road2 , lightingON ,lightingOFF ,lightingERROR , rightLightON, leftLightON, rightLightOFF, leftLightOFF,batterycharge, batterylevel;
    float roadHeight = 600f; // match height in dp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

         videoView = findViewById(R.id.videoView);

        lightingON=findViewById(R.id.light_on_img);
        lightingOFF=findViewById(R.id.light_off_img);
        lightingERROR=findViewById(R.id.light_error_img);
        rightLightON= findViewById(R.id.right_on_img);
        rightLightOFF= findViewById(R.id.right_off_img);
        leftLightON= findViewById(R.id.left_on_img);
        leftLightOFF= findViewById(R.id.left_off_img);

        batterycharge = findViewById(R.id.charge_img);
        batterylevel =findViewById(R.id.battery4_img);
        charge_txt = findViewById(R.id.charge_txt);

        gearRecycler = findViewById(R.id.gearRecycler);


        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.road_moving4;
        Uri uri = Uri.parse(videoPath);

        videoView.setVideoURI(uri);

        // Add play/pause controls
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.start(); // start playing automatically

        // Use horizontal layout for gear shift
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        gearRecycler.setLayoutManager(layoutManager);

        // Set the adapter
        GearAdapter adapter = new GearAdapter();
        gearRecycler.setAdapter(adapter);

        adapter.setSelectedPosition(1);
        handleLighting();
        handleRightLight();
        handleLeftLight();
        handleBatteryLevel();
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
    private void handleLighting(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Background work
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(vhallighting_status == 1) {
                            lightingON.setVisibility(ImageView.VISIBLE);
                            lightingOFF.setVisibility(ImageView.INVISIBLE);
                            lightingERROR.setVisibility(ImageView.INVISIBLE);
                        } else if(vhallighting_status == 0) {
                            lightingON.setVisibility(ImageView.INVISIBLE);
                            lightingOFF.setVisibility(ImageView.VISIBLE);
                            lightingERROR.setVisibility(ImageView.INVISIBLE);
                        } else if(vhallighting_status == -1) {
                            lightingON.setVisibility(ImageView.INVISIBLE);
                            lightingOFF.setVisibility(ImageView.INVISIBLE);
                            lightingERROR.setVisibility(ImageView.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }
    private void handleRightLight() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Background work
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(vhalright_light_status == 1) {
                            rightLightON.setVisibility(ImageView.VISIBLE);
                            rightLightOFF.setVisibility(ImageView.INVISIBLE);
                        } else if(vhalright_light_status == 0) {
                            rightLightON.setVisibility(ImageView.INVISIBLE);
                            rightLightOFF.setVisibility(ImageView.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }
    private void handleLeftLight() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Background work
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(vhalleft_light_status == 1) {
                            leftLightON.setVisibility(ImageView.VISIBLE);
                            leftLightOFF.setVisibility(ImageView.INVISIBLE);
                        } else if(vhalleft_light_status == 0) {
                            leftLightON.setVisibility(ImageView.INVISIBLE);
                            leftLightOFF.setVisibility(ImageView.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }
    private void handleBatteryLevel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Background work
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        charge_txt.setText(vhalbatterylevel + "%");
                        if(vhalbatterylevel >= 80) {
                            batterylevel.setImageResource(R.drawable.battery5);
                            batterycharge.setVisibility(ImageView.INVISIBLE);
                        } else if(vhalbatterylevel >= 50) {
                            batterylevel.setImageResource(R.drawable.battery4);
                            batterycharge.setVisibility(ImageView.INVISIBLE);

                        } else if(vhalbatterylevel >= 20) {
                            batterylevel.setImageResource(R.drawable.battery3);
                            batterycharge.setVisibility(ImageView.INVISIBLE);
                        } else {
                            batterylevel.setImageResource(R.drawable.battery2);
                            batterycharge.setVisibility(ImageView.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

}