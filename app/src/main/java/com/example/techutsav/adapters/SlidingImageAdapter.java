package com.example.techutsav.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.techutsav.R;
import com.example.techutsav.fragments.MainActivityNew;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {


    private final String TAG = "Adapter";
    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImageAdapter(Context context,ArrayList<Integer> IMAGES) {
        Log.e(TAG, "SlidingImageAdapter: inside adapter" );
        this.context = context;
        this.IMAGES=IMAGES;
        Log.e(TAG, "SlidingImageAdapter: "+IMAGES.size() );
        inflater = LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.pager_image, view, false);

        ImageView imageView = imageLayout
                .findViewById(R.id.image);

        imageView.setImageResource(IMAGES.get(position));
       // imageView.setBackgroundResource(IMAGES.get(position));
        view.addView(imageLayout, 0);

        return imageLayout;
    }




    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
