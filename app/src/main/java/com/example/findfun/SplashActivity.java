package com.example.findfun;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView img;
    TextView firstF, secondInd, thirdY, fourthOur, fifthF, sixthUn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        img = findViewById(R.id.iconbtn);
        firstF = findViewById(R.id.F);
        secondInd = findViewById(R.id.ind);
        thirdY = findViewById(R.id.Y);
        fourthOur = findViewById(R.id.our);
        fifthF = findViewById(R.id.F2);
        sixthUn = findViewById(R.id.un);

        img.setAnimation(topAnim);
        firstF.setAnimation(bottomAnim);
        secondInd.setAnimation(bottomAnim);
        thirdY.setAnimation(bottomAnim);
        fourthOur.setAnimation(bottomAnim);
        fifthF.setAnimation(bottomAnim);
        sixthUn.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
        }, 2900);
    }
}