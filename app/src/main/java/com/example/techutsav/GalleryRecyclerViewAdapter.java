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

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder> {

     Context context;
     List<GalleryList> galleryLists;
     FragmentActivity activity;


     public GalleryRecyclerViewAdapter(List<GalleryList> galleryLists, Context context, FragmentActivity activity){

         this.galleryLists = galleryLists;
         this.context = context;
         this.activity = activity;

     }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_card_view,parent,false);
        return new GalleryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        Glide.with(context)
//                .load(galleryLists.get(position).getgImage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transition(withCrossFade())
//                .placeholder(R.drawable.placeholder)
//                .into(holder.galleryImage);


    }



    @Override
    public int getItemCount() {
        return 51;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         ImageView galleryImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryImage = itemView.findViewById(R.id.gallery_image_view);

        }
     }



}
