package com.example.asus.go_paradmin.ui.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.ui.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imgLogo = findViewById(R.id.img_logo);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.logo_transition);
        imgLogo.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent (SplashScreenActivity.this, LoginActivity.class);
                SplashScreenActivity.this.startActivity(loginIntent);
                SplashScreenActivity.this.finish();
            }
        }, 4000);
    }
}
