package com.example.techutsav.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techutsav.R;
import com.example.techutsav.models.participantDetailCell;

import java.util.ArrayList;

public class PTAdapter extends RecyclerView.Adapter<PTAdapter.ViewHolder> {

    ArrayList<participantDetailCell> listData;
    Context context;

    public boolean visibility;

    public PTAdapter(ArrayList<participantDetailCell> listData, Context context) {


        this.listData = listData;
        visibility = false;
        this.context = context;
        Log.e("TAG", "PTAdapter: " + listData.size());

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.participant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.serial_num.setText(position + 1 + "");
        if (listData.get(position) != null) {
            String s = "";
            //names=listData.get(position).getName();
            if (listData.get(position).getParticipantName() != null) {
                for (int i = 0; i < listData.get(position).getParticipantName().size(); i++)
                    s = s + listData.get(position).getParticipantName().get(i)+ ", ";
            }
            String s1 = "";
            //contact=listData.get(position).getName();
            if (listData.get(position).getParticipantDept() != null) {

                for (int i = 0; i < listData.get(position).getParticipantDept().size(); i++)
                    s1 = s1+ listData.get(position).getParticipantDept().get(i)+ ", ";
            }
            String s2 = "";
            //contact=listData.get(position).getName();
            if (listData.get(position).getParticipantRegno() != null) {

                for (int i = 0; i < listData.get(position).getParticipantRegno().size(); i++)
                    s2 = s2 + listData.get(position).getParticipantRegno().get(i)+ ", ";

            }
            holder.teammem.setText("Team Memeber: "+s);
            holder.dept.setText(s1);
            holder.regno.setText(s2);
            holder.email.setText("Email: "+listData.get(position).getEmail());
            holder.buphno.setText(listData.get(position).getBackupPhone());
            holder.contact.setText(listData.get(position).getPhoneno());
            holder.college.setText("College: "+listData.get(position).getCollege());

            if (listData.get(position).getEventID().equals("eve01") || listData.get(position).getEventID().equals("eve02")) {
                holder.topic.setText(listData.get(position).getTopic());
            } else {
                holder.topic.setVisibility(View.GONE);
            }


            if (listData.get(position).getEventID().equals("eve05")) {
                if (listData.get(position).getGame().equals("PUBG")) {
                    holder.squad.setText(listData.get(position).getSquadName());
                } else
                    holder.squad.setVisibility(View.GONE);
                holder.game.setText(listData.get(position).getGame());
            } else {
                holder.game.setVisibility(View.GONE);
                holder.squad.setVisibility(View.GONE);
            }

            holder.card.setOnClickListener(view -> {

                if (!visibility) {
                    holder.ll.setVisibility(View.VISIBLE);
                    visibility = true;
                } else {
                    holder.ll.setVisibility(View.GONE);
                    visibility = false;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView college, teammem, contact, email, serial_num, squad, topic, buphno, regno, game, dept;

        CardView card;

        LinearLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dept = itemView.findViewById(R.id.dept);
            teammem = itemView.findViewById(R.id.team_members);
            college = itemView.findViewById(R.id.college_name);
            contact = itemView.findViewById(R.id.contacts);
            email = itemView.findViewById(R.id.email);
            squad = itemView.findViewById(R.id.sqaud);
            topic = itemView.findViewById(R.id.topic);
            buphno = itemView.findViewById(R.id.backup_phno);
            regno = itemView.findViewById(R.id.regno);
            game = itemView.findViewById(R.id.game);
            card = itemView.findViewById(R.id.pt_card);
            ll = itemView.findViewById(R.id.cardinvisible);
            serial_num = itemView.findViewById(R.id.serial_num);

        }
    }
}
