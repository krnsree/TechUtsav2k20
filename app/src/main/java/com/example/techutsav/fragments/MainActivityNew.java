package com.example.techutsav.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.techutsav.R;
import com.example.techutsav.adapters.SlidingImageAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityNew extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.banner_base)
    RelativeLayout bannerBase;
    @BindView(R.id.events_card)
    LinearLayout eventsCard;
    @BindView(R.id.gallery_card)
    LinearLayout galleryCard;
    @BindView(R.id.alumni_card)
    LinearLayout alumniCard;
    @BindView(R.id.info_card)
    LinearLayout infoCard;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final int[] IMAGES = {R.drawable.title, R.drawable.adzap, R.drawable.alpha, R.drawable.bugbuster, R.drawable.cinephillia
            , R.drawable.fotographia, R.drawable.mind_bender, R.drawable.paper, R.drawable.poster};
    @BindView(R.id.cards)
    LinearLayout cards;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

       cards.setVisibility(View.VISIBLE);
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        pager.setAdapter(new SlidingImageAdapter(this, ImagesArray));


        CirclePageIndicator indicator = findViewById(R.id.indicator);

        indicator.setViewPager(pager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            pager.setCurrentItem(currentPage++, true);
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    @OnClick({R.id.events_card, R.id.gallery_card, R.id.alumni_card, R.id.info_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.events_card:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                        , new EventFragment()).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.gallery_card:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                        , new GalleryFragment()).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.alumni_card:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                        , new AlumniFragment()).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.info_card:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                        , new AboutFragment()).addToBackStack(null).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: ");


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: ");
        cards.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        cards.setVisibility(View.VISIBLE);
        Log.e("TAG", "onStart: ");
    }
}
