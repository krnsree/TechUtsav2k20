package com.fsh.techutsav.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.fsh.techutsav.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddResult extends Fragment {

    @BindView(R.id.textViewAdd)
    Button add;

    @BindView(R.id.finaltextViewAdd)
    Button finaladd;

    @BindView(R.id.textViewRemove)
    Button remove;

    @BindView(R.id.finaltextViewRemove)
    Button finalremove;

    @BindView(R.id.memeber1)
    TextView member1;
    @BindView(R.id.memeber2)
    TextView member2;
    @BindView(R.id.memeber3)
    TextView member3;
    @BindView(R.id.memeber4)
    TextView member4;

    @BindView(R.id.memeber2title)
    LinearLayout member2title;

    @BindView(R.id.finalmemeber2title)
    LinearLayout finalmember2title;

    @BindView(R.id.memeber3title)
    LinearLayout member3title;

    @BindView(R.id.finalmemeber3title)
    LinearLayout finalmember3title;

    @BindView(R.id.memeber4title)
    LinearLayout member4title;

    @BindView(R.id.finalmemeber4title)
    LinearLayout finalmember4title;

    @BindView(R.id.finalsmem)
    LinearLayout finalmem;

    @BindView(R.id.prelimsmem)
    LinearLayout prelimsmem;

    @BindView(R.id.college)
    TextView college;

    @BindView(R.id.prelimsCard)
    CardView prelimsCard;

    @BindView(R.id.resultCard)
    CardView finalCard;

    @BindView(R.id.AddResult)
    Button paddResulst;

    @BindView(R.id.closeCard)
    Button pcloseCard;

    String eventid;

    FirebaseFirestore ref;

    int member_count = 1;
    int final_member_count = 1;

    ArrayList<String> memebersList = new ArrayList<>();


    boolean visibility = false;
    boolean final_visibility = false;

    public AddResult(String eventid) {
        this.eventid = eventid;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_result, container, false);

        ButterKnife.bind(this, view);

        ref=FirebaseFirestore.getInstance();

        if (!(eventid.equals("eve004") || eventid.equals("eve006"))) {
            prelimsCard.setVisibility(View.GONE);
        } else
            setPrelims();

        setFinals();

        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.GONE);
        return view;
    }

    private void setFinals() {

        finalCard.setOnClickListener(view -> {

            if (!final_visibility) {
                finalmem.setVisibility(View.VISIBLE);
                final_visibility = true;
            } else {
                finalmem.setVisibility(View.GONE);
                final_visibility = false;
            }
        });

        finaladd.setOnClickListener(view13 -> {


            if (final_member_count < 4)
                final_member_count++;
            else
                Toast.makeText(getContext(), "No more Members can be added", Toast.LENGTH_LONG).show();

            Log.e("TAG", "onClick: " + getContext());

            switch (final_member_count) {
                case 2:
                    finalmember2title.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    finalmember3title.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    finalmember4title.setVisibility(View.VISIBLE);
                    break;
            }
        });

        finalremove.setOnClickListener(view14 -> {

            if (final_member_count > 1)
                final_member_count--;
            else
                Toast.makeText(getContext(), "No more Members can be removed", Toast.LENGTH_LONG).show();

            Log.e("TAG", "onClick: " + getContext());

            switch (final_member_count) {
                case 1:
                    finalmember2title.setVisibility(View.GONE);
                    break;
                case 2:
                    finalmember3title.setVisibility(View.GONE);
                    break;
                case 3:
                    finalmember4title.setVisibility(View.GONE);
                    break;
            }

        });

    }

    private void setPrelims() {

        prelimsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!visibility) {
                    prelimsmem.setVisibility(View.VISIBLE);
                    visibility = true;
                } else {
                    prelimsmem.setVisibility(View.GONE);
                    visibility = false;
                }
            }
        });

        add.setOnClickListener(view1 -> {

            if (member_count < 4)
                member_count++;
            else
                Toast.makeText(getContext(), "No more Members can be added", Toast.LENGTH_LONG).show();

            Log.e("TAG", "onClick: " + getContext());

            switch (member_count) {
                case 2:
                    member2title.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    member3title.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    member4title.setVisibility(View.VISIBLE);
                    break;
            }
        });

        remove.setOnClickListener(view12 -> {
            if (member_count > 1)
                member_count--;
            else
                Toast.makeText(getContext(), "No more Members can be added", Toast.LENGTH_LONG).show();

            Log.e("TAG", "onClick: " + getContext());

            switch (member_count) {
                case 1:
                    member2title.setVisibility(View.GONE);
                    break;
                case 2:
                    member3title.setVisibility(View.GONE);
                    break;
                case 3:
                    member4title.setVisibility(View.GONE);
                    break;
            }
        });

        paddResulst.setOnClickListener(view -> {

            for (int i = 1; i <= member_count; i++) {
                switch (i) {
                    case 1:
                        memebersList.add(member1.getText().toString());
                        break;
                    case 2:
                        memebersList.add(member2.getText().toString());
                        break;
                    case 3:
                        memebersList.add(member3.getText().toString());
                        break;
                    case 4:
                        memebersList.add(member4.getText().toString());
                        break;
                }
            }

            Toast.makeText(getContext(), "Added succesfully", Toast.LENGTH_LONG).show();

        });
    }

    @Override
    public void onStop() {
        super.onStop();
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.VISIBLE);
    }
}