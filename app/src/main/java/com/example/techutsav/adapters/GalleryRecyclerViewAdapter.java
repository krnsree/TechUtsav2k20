package com.example.techutsav.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.techutsav.R;
import com.example.techutsav.fragments.ImageViewFragment;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Bitmap> galleryLists;
    private FragmentActivity activity;


    public GalleryRecyclerViewAdapter(Context context, ArrayList<Bitmap> galleryLists, FragmentActivity activity) {

        this.galleryLists = galleryLists;
        this.context = context;
        this.activity = activity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_card_view, parent, false);
        return new GalleryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(context)
                .load(galleryLists.get(position))
                .into(holder.galleryImage);
        holder.galleryImage.setOnClickListener(v -> {
            ImageViewFragment imageViewFragment = new ImageViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("image_count", String.valueOf(position));
            imageViewFragment.setArguments(bundle);
            imageViewFragment.show(activity.getSupportFragmentManager(), "");


        });
    }


    @Override
    public int getItemCount() {
        return galleryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView galleryImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryImage = itemView.findViewById(R.id.gallery_image_view);

        }
    }


}
