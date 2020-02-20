package com.example.techutsav.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.techutsav.models.EventDataCell;
import com.example.techutsav.R;
import com.example.techutsav.fragments.Event_Page;
import com.google.gson.Gson;


import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.VIewHolder> {

    Context context;
    List<EventDataCell> eventList;
    FragmentActivity activity;

    public EventRecyclerViewAdapter(List<EventDataCell> eventList, Context context, FragmentActivity activity) {

        this.activity = activity;
        this.eventList = eventList;
        this.context = context;
    }


    @NonNull
    @Override
    public VIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_view, parent, false);
        return new EventRecyclerViewAdapter.VIewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolder holder, final int position) {

        holder.eventTitle.setText(eventList.get(position).getTitle());
        setImage(holder, position);

        holder.itemView.setOnClickListener(view -> {
            Gson gson = new Gson();
            String list = gson.toJson(eventList.get(position));
            Bundle bundle = new Bundle();
            Log.e("Tag", "onBindViewHolder: "+position );
            bundle.putString("EVENT_DATA", list);
            bundle.putString("POS", String.valueOf(position));
            Event_Page event_page = new Event_Page(context,activity);
            event_page.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
            , event_page ).addToBackStack(null).commitAllowingStateLoss();
        });
    }

    private void setImage(VIewHolder holder, int position) {
        if (position == 0){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.paper)
                    .into(holder.eventImage);
        }else if (position == 1){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.poster)
                    .into(holder.eventImage);
        }else if (position == 2){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.fotographia)
                    .into(holder.eventImage);
        }else if (position == 3){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.mind_bender)
                    .into(holder.eventImage);
        }else if (position == 4){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.alpha)
                    .into(holder.eventImage);
        }else if (position == 5){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.bugbuster)
                    .into(holder.eventImage);
        }else if (position == 6){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.adzap)
                    .into(holder.eventImage);
        }else if (position == 7){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.troll)
                    .into(holder.eventImage);
        }else if (position == 9){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.special)
                    .into(holder.eventImage);
        }else if (position == 10){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.cinephillia)
                    .into(holder.eventImage);
        }else if (position == 8){
            Glide.with(context)
                    .load(eventList.get(position).getImageUrl())
                    .placeholder(R.drawable.xpresso)
                    .into(holder.eventImage);
        }


    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class VIewHolder extends RecyclerView.ViewHolder {

        ImageView eventImage;
        TextView eventTitle;

        public VIewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.event_frag_title);
            eventImage = itemView.findViewById(R.id.event_frag_image_view);
        }
    }
}


