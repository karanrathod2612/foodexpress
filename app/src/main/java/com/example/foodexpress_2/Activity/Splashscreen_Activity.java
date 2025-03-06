package com.example.foodexpress_2.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodexpress_2.R;
import com.example.foodexpress_2.databinding.ActivitySplashscreenBinding;

public class Splashscreen_Activity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ActivitySplashscreenBinding binding;
    VideoView animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splashscreen_Activity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);


        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        animation = findViewById(R.id.splashscreen);
        setvariable();
        String vPath = "android.resource://"+getPackageName()+"/raw/logoanimation";
        Uri videouri = Uri.parse(vPath);
        animation.setVideoURI(videouri);
        animation.start();
    }
    private void setvariable() {
    }
}