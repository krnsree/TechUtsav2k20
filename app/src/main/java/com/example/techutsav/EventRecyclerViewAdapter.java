package com.example.techutsav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.VIewHolder> {


    public EventRecyclerViewAdapter(Context context) {
    }

    @NonNull
    @Override
    public VIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_view,parent,false);
        return new EventRecyclerViewAdapter.VIewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public class VIewHolder extends RecyclerView.ViewHolder {
        public VIewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
