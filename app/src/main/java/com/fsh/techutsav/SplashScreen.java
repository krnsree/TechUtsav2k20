package com.fsh.techutsav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.fsh.techutsav.fragments.MainActivityNew;

import butterknife.ButterKnife;

public class SplashScreen extends Activity {


    final int SPLASH_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent mySuperIntent = new Intent(SplashScreen.this, MainActivityNew.class);
//        startActivity(mySuperIntent);
//        finish();
          setContentView(R.layout.activity_splash_screen2);

        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Context context = this;

        Log.e("TAG", "onCreate: " + context);
        new Handler().postDelayed(() -> {
            Intent mySuperIntent = new Intent(SplashScreen.this, MainActivityNew.class);
            startActivity(mySuperIntent);
            finish();
        }, SPLASH_TIME);
    }


}
