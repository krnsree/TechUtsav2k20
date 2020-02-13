package com.example.techutsav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));


        if(savedInstanceState == null){

             getSupportFragmentManager()
                     .beginTransaction()
                     .add(R.id.mainFrame, new EventFragment())
                     .commit();
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_nav);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                 switch (item.getItemId()){

                     case R.id.bottom_nav_event_btn :
                                  navigateTo(new EventFragment(),false);
                                  return true;
                     case R.id.bottom_nav_schedule_btn :
                                   navigateTo(new AlumniFragment(),false);
                                   return true;
                     case R.id.bottom_nav_gallery_btn:
                         navigateTo(new GalleryFragment(),false);
                         return true;
                     case R.id.bottom_nav_info_btn:
                                   navigateTo(new AboutFragment(), false);
                                   return true;
                 }

                 return false;
            }
        });

    }


    @Override
    public void navigateTo(Fragment fragment, boolean backStack) {

        FragmentTransaction transaction =
                      getSupportFragmentManager()
                      .beginTransaction()
                      .replace(R.id.mainFrame, fragment);
                       transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                if(backStack){

                    transaction.addToBackStack(null);
                }


                transaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
