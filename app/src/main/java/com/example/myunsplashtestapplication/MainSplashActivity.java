package com.example.myunsplashtestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainSplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3500;
    //Variables
    Animation topAnim, bottomAnim;
    TextView textViewLogo;
    ImageView imageViewLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //Hooks
        textViewLogo = findViewById(R.id.textViewLogo);
        imageViewLogo = findViewById(R.id.imageViewLogo);

        textViewLogo.setAnimation(topAnim);
        imageViewLogo.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainSplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}
