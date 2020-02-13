package com.example.techutsav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder> {

     Context context;
     ArrayList<GalleryList> galleryLists;



     public GalleryRecyclerViewAdapter(Context context, ArrayList<GalleryList> galleryLists){

         this.galleryLists = galleryLists;
         this.context = context;
     }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_card_view,parent,false);
        return new GalleryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.galleryImage.setImageResource(galleryLists.get(position).getgImagge());

//        Glide.with(context)
//                .load(galleryLists.get(position).getgImagge())
//                .into(holder.galleryImage);
     }



    @Override
    public int getItemCount() {
        return galleryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         ImageView galleryImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryImage = itemView.findViewById(R.id.gallery_image_view);

        }
     }



}
