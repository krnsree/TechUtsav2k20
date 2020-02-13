package com.example.techutsav.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.techutsav.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewFragment extends AppCompatDialogFragment {


    @BindView(R.id.image_loader)
    ProgressBar imageLoader;
    private Dialog dialog;
    @BindView(R.id.product_image_close_tv)
    TextView productImageCloseTv;
    @BindView(R.id.product_images_list_rv)
    RecyclerView productImagesListRv;
    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScroll;

    private View view;
    private String imageCount;
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
    private ProductImageListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.image_fragment, container, false);
        ButterKnife.bind(this, view);
        dialog = new Dialog(getContext());
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getDialog().getWindow().setLayout((6 * width)/7, (6 * height)/7);

        if (getArguments() != null) {
            imageCount = getArguments().getString("image_count");
        }
        productImageCloseTv.setOnClickListener(v -> getDialog().cancel());
        productImagesListRv.setHasFixedSize(true);
        productImagesListRv.setNestedScrollingEnabled(true);
        productImagesListRv.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        productImagesListRv.setLayoutManager(layoutManager);
        final PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(productImagesListRv);
        loaderOn();
        new ConvertImage().execute();
        return view;
    }

    private void loaderOn() {
        imageLoader.setVisibility(View.VISIBLE);
        productImagesListRv.setVisibility(View.INVISIBLE);

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

        ArrayList<Bitmap> firstHalf = new ArrayList<>();
        ArrayList<Bitmap> secondHalf = new ArrayList<>();
        for (int i = 0; i < bitmaps.size(); i++) {
            if (i < Integer.valueOf(imageCount)) {
                secondHalf.add(bitmaps.get(i));
            } else {
                firstHalf.add(bitmaps.get(i));
            }
        }

        firstHalf.addAll(secondHalf);
        adapter = new ProductImageListAdapter(getContext(), firstHalf);
        productImagesListRv.setAdapter(adapter);
        loaderOff();

    }

    private void loaderOff() {
        imageLoader.setVisibility(View.GONE);
        productImagesListRv.setVisibility(View.VISIBLE);

    }


    public class ProductImageListAdapter extends RecyclerView.Adapter<ProductImageListAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Bitmap> imageList;

        public ProductImageListAdapter(Context mContext, ArrayList<Bitmap> imageList) {
            super();
            this.mContext = mContext;
            dialog = new Dialog(this.mContext);
            this.imageList = imageList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_recycler, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Glide.with(mContext)
                    .load(imageList.get(position))
                    .into(holder.product_image_icon_iv);
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView product_image_icon_iv;
            public CardView card_view;

            public MyViewHolder(View view) {
                super(view);

                card_view = view.findViewById(R.id.card_view);
                product_image_icon_iv = view.findViewById(R.id.product_image_icon_iv);
            }
        }
    }

}
