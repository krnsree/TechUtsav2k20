package com.example.techutsav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.VIewHolder> {

    Context context;
    List<EventDataCell> eventList;


    public EventRecyclerViewAdapter(List<EventDataCell> eventList, Context context) {

        this.eventList = eventList;
        this.context = context;
    }




    @NonNull
    @Override
    public VIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_view,parent,false);
        context = parent.getContext();
        return new EventRecyclerViewAdapter.VIewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolder holder, int position) {


        holder.eventTitle.setText(eventList.get(position).getName());

        String ImageUrl = eventList.get(position).getImageUrl();
        holder.setEventImage(ImageUrl);


    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class VIewHolder extends RecyclerView.ViewHolder {

        String eventImage;
        TextView eventTitle;
        View view;



        public VIewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            eventTitle = itemView.findViewById(R.id.event_frag_title);


        }

        public void setEventImage(String eventImage) {
            this.eventImage = eventImage;
            ImageView image_event_finder = itemView.findViewById(R.id.event_frag_image_view);
            Glide.with(context)
                    .load(eventImage)
                    .into(image_event_finder);

        }
    }
}
