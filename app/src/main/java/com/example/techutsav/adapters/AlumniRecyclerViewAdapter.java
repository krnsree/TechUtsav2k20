package com.example.techutsav.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.techutsav.R;
import com.example.techutsav.models.AlumniList;


import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AlumniRecyclerViewAdapter extends RecyclerView.Adapter<AlumniRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<AlumniList> aList;
    FragmentActivity activity;


    public AlumniRecyclerViewAdapter(Context context, ArrayList<AlumniList> list, FragmentActivity activity){

         this.context = context;
         this.aList = list;
         this.activity = activity;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alumni_card_layout,parent,false);
        return new AlumniRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(aList.get(position).getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .placeholder(R.drawable.placeholder)
                .into(holder.rImg);
        holder.rName.setText(aList.get(position).getName());
        holder.rDesignation.setText(aList.get(position).getDesignation());
        holder.rQuotes.setText(aList.get(position).getQuotes());
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rDesignation;
        ImageView rImg;
        TextView rName;
        TextView rQuotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rDesignation = itemView.findViewById(R.id.alumni_designation);
            rImg = itemView.findViewById(R.id.alumni_image);
            rName = itemView.findViewById(R.id.alumni_name);
            rQuotes = itemView.findViewById(R.id.alumni_quotes);
        }

    }
}
