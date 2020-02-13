package com.example.techutsav.fragments;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techutsav.adapters.GalleryRecyclerViewAdapter;
import com.example.techutsav.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GalleryFragment extends Fragment {

    @BindView(R.id.image_loader)
    ProgressBar imageLoader;
    private RecyclerView galleryRecycler;
    private GalleryRecyclerViewAdapter adapter;

    private final int img[] = {

            R.drawable.img,
            R.drawable.img1,
            R.drawable.img11,
            R.drawable.img18,
            R.drawable.img2,
            R.drawable.img20,
            R.drawable.img21,
            R.drawable.img28,
            R.drawable.img29,
            R.drawable.img3,
            R.drawable.img30,
            R.drawable.img32,
            R.drawable.img33,
            R.drawable.img34,
            R.drawable.img37,
            R.drawable.img4,
            R.drawable.img40,
            R.drawable.img41,
            R.drawable.img45,
            R.drawable.img5,
    };
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        galleryRecycler = view.findViewById(R.id.gallery_recycler_view);
        galleryRecycler.setHasFixedSize(true);
        galleryRecycler.setItemViewCacheSize(20);
        galleryRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));


        getGalleryData();

        return view;
    }


    private void getGalleryData() {
        loaderOn();
        new ConvertImage().execute();
    }

    private void loaderOn(){
        imageLoader.setVisibility(View.VISIBLE);
        galleryRecycler.setVisibility(View.GONE);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private class ConvertImage extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Bitmap bitmap;
            for (int i = 0; i < img.length; i++) {
                bitmap = decodeSampledBitmapFromResource(getContext().getResources()
                        , img[i], 100, 100);
                bitmaps.add(bitmap);
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            setImages();

        }
    }

    private void setImages() {
        loaderOff();
        adapter = new GalleryRecyclerViewAdapter(getContext(), bitmaps, getActivity());
        galleryRecycler.setAdapter(adapter);
    }

    private void loaderOff() {
        imageLoader.setVisibility(View.GONE);
        galleryRecycler.setVisibility(View.VISIBLE);
    }
}
