package com.example.techutsav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class PTAdapter extends RecyclerView.Adapter<PTAdapter.ViewHolder> {

    ArrayList<participantDetailCell> listData;
    Context context;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> contact = new ArrayList<>();
    public boolean visibility;

    public PTAdapter(ArrayList<participantDetailCell> listData, Context context) {


        this.listData = listData;
        visibility = false;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.participant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.serial_num.setText(position+1+"");
        holder.teamname.setText("Team Name: "+listData.get(position).getTeam_name());
        holder.teamrep.setText(listData.get(position).getTeam_rep());
        holder.college.setText("College: "+listData.get(position).getCollege());

        String s = "";
        //names=listData.get(position).getName();
        for (int i = 0; i < listData.get(position).getName().size(); i++)
            s = s + "," + listData.get(position).getName().get(i);

        String s1 = "";
        //contact=listData.get(position).getName();
        for (int i = 0; i < listData.get(position).getContact().size(); i++)
            s1 = s1 + "," + listData.get(position).getContact().get(i);

        holder.contact.setText(s1);
        holder.teammem.setText(s);
        holder.email.setText(listData.get(position).getEmail());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!visibility) {
                    holder.ll.setVisibility(View.VISIBLE);
                    visibility=true;
                } else {
                    holder.ll.setVisibility(View.GONE);
                    visibility=false;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView college, teamname, teamrep, teammem, contact, email,serial_num;

        CardView card;

        LinearLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            college = itemView.findViewById(R.id.college_name);
            teamname = itemView.findViewById(R.id.team_name);
            teammem = itemView.findViewById(R.id.team_mem);
            teamrep = itemView.findViewById(R.id.team_rep);
            contact = itemView.findViewById(R.id.contacts);
            email = itemView.findViewById(R.id.email);
            card = itemView.findViewById(R.id.pt_card);
            ll = itemView.findViewById(R.id.cardinvisible);
            serial_num=itemView.findViewById(R.id.serial_num);
        }
    }
}
