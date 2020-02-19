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

import com.example.techutsav.R;
import com.example.techutsav.models.DevDataCell;

import java.util.ArrayList;

public class DevInfoRecyclerViewAdapter extends RecyclerView.Adapter<DevInfoRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<DevDataCell> devList;
    FragmentActivity activity;





    public DevInfoRecyclerViewAdapter(Context context, ArrayList<DevDataCell> devList, FragmentActivity activity){

        this.context = context;
        this.devList = devList;
        this.activity = activity;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dev_info_card,parent,false);
        return new DevInfoRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.name.setText(devList.get(position).getName());
        holder.designation.setText(devList.get(position).getDesination());
        holder.dept.setText(devList.get(position).getDept());
        holder.year.setText(devList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return devList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

         ImageView image;
         TextView  name;
         TextView  designation;
         TextView  dept;
         TextView  year;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            image = itemView.findViewById(R.id.dev_img);
            name = itemView.findViewById(R.id.dev_name);
            designation = itemView.findViewById(R.id.dev_designation);
            dept = itemView.findViewById(R.id.dev_dept);
            year = itemView.findViewById(R.id.dev_year);


        }
    }
}
