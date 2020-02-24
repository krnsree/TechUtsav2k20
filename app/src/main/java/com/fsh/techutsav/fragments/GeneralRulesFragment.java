package com.fsh.techutsav.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.fsh.techutsav.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralRulesFragment extends Fragment {

    @BindView(R.id.generalRules)
    TextView generalRules;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.grpb)
    ProgressBar pb;

    private static ArrayList<String> rules = new ArrayList<>();

    FirebaseFirestore ref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_rules, container, false);
        ButterKnife.bind(this, view);

        ref = FirebaseFirestore.getInstance();
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.GONE);
        toolbar.setTitle("TechUtsav");
        getData();
        return view;
    }

    private void getData() {

        ref.collection("Rules")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e("TAG", "general: " + task.isSuccessful());

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            rules = (ArrayList<String>) document.getData().get("rules");
                            Log.e("TAG", "generalT: " + rules);
                        }

                        String s = "";

                        Log.e("TAG", "generalhere " + rules);

                        for (int i = 0; i < rules.size(); i++) {
                            s = s + (i + 1) + ").\t" + rules.get(i) + "\n\n";
                        }

                        pb.setVisibility(View.GONE);
                        Log.e("TAG", "generalhere" + s);
                        generalRules.setText(s);
                    }
                });

    }

    @Override
    public void onStop() {
        super.onStop();
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.VISIBLE);
    }
}
