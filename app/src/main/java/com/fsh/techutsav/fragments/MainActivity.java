package com.fsh.techutsav.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.fsh.techutsav.models.NavigationHost;
import com.fsh.techutsav.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));


        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainFrame, new EventFragment())
                    .addToBackStack(null)
                    .commit();
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_nav);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_nav_event_btn:
                        navigateTo(new EventFragment(), false);
                        return true;
                    case R.id.bottom_nav_schedule_btn:
                        navigateTo(new AlumniFragment(), false);
                        return true;
                    case R.id.bottom_nav_gallery_btn:
                        navigateTo(new GalleryFragment(), false);
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

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
        , fragment).addToBackStack(null).commitAllowingStateLoss();
       /* FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (backStack) {

            transaction.addToBackStack(null);
        }


        transaction.commit();*/
    }


}
