package com.example.dashboardscreen;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.hardware.lights.Light;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.car.Car
//import android.car.hardware.CarPropertyValue
//import android.car.hardware.property.CarPropertyManager

public class MainActivity extends AppCompatActivity {

    int VENDOR_EXTENSION_SPEED_PROPERTY = 0x21400104;

    int VENDOR_EXTENSION_GEAR_PROPERTY = 0x21400105;

    int VENDOR_EXTENSION_BATTERYLEVEL_PROPERTY = 0x21400106;

    int VENDOR_EXTENSION_RIGHTLIGHTING_PROPERTY = 0x21400107;

    int VENDOR_EXTENSION_LEFTLIGHTING_PROPERTY = 0x21400108;
    int VENDOR_EXTENSION_LIGHTING_PROPERTY = 0x21400108;
    int VENDOR_EXTENSION_TEMP_PROPERTY = 0x21400109;
    int VENDOR_EXTENSION_TIRE_PROPERTY  = 0x21400110;
    int VENDOR_EXTENSION_SEATBELT_PROPERTY  = 0x21400110;

    int VENDOR_EXTENSION_DISTANCE_PROPERTY= 0x21400110;



    int vhallighting_status=1,vhalright_light_status=0,vhalleft_light_status =1,vhalbatterylevel=15;
    RecyclerView gearRecycler;

    TextView charge_txt;

    ImageView road1, road2 , lightingON ,lightingOFF ,lightingERROR , rightLightON, leftLightON, rightLightOFF, leftLightOFF,batterycharge, batterylevel;
    float roadHeight = 600f; // match height in dp

//    CarPropertyManager carPropertyManager;

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
//        car = Car.createCar(this.applicationContext);
//        carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager;

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

        // Use horizontal layout for gear shift
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        gearRecycler.setLayoutManager(layoutManager);

        // Set the adapter
        GearAdapter adapter = new GearAdapter();
        gearRecycler.setAdapter(adapter);

        adapter.setSelectedPosition(1);

//        carPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
//            @Override
//            public void onChangeEvent(@Nullable CarPropertyValue<?> value) {
//                if (value != null && value.getValue() instanceof Integer) {
//                    int intValue = (Integer) value.getValue();
//                    updateSpeedometer(1, (float) intValue);
//                }
//            }
//
//            @Override
//            public void onErrorEvent(int propertyId, int zone) {
//                Log.i("Prop Error", propertyId + " , " + zone);
//            }
//        }, VENDOR_EXTENSION_SPEED_PROPERTY, CarPropertyManager.SENSOR_RATE_FASTEST);
        
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