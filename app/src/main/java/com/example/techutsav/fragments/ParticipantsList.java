package com.example.techutsav.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techutsav.R;
import com.example.techutsav.adapters.PTAdapter;
import com.example.techutsav.models.participantDetailCell;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ParticipantsList extends Fragment {

    private final String eventid;
    /*@BindView(R.id.pt_toolbar)
    Toolbar toolbar;*/

    @BindView(R.id.pt_rv)
    RecyclerView pt_rv;

    @BindView(R.id.textNone)
    TextView none;

    PTAdapter ptadapter;


    private boolean isDataAvailable;

    private ArrayList<participantDetailCell> listData = new ArrayList<>();
    private String TAG = "TAG";

    @BindView(R.id.loading)
    ShimmerFrameLayout loading;

    public ParticipantsList(String eventId) {
        this.eventid = eventId;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_participants_list, container, false);

        ButterKnife.bind(this, view);


        /*if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }*/

        getData();
        ptadapter = new PTAdapter(listData, getContext());
        pt_rv.setAdapter(ptadapter);

        return view;
    }

    private void getData() {


        if (listData != null && listData.size() > 0) {
            isDataAvailable = false;
            loading.setVisibility(View.GONE);
            loading.startShimmerAnimation();
            return;
        }

        FirebaseFirestore db=FirebaseFirestore.getInstance();

        loading.startShimmerAnimation();
        db.collection("Participants")
                .whereEqualTo("eventid", eventid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (!task.getResult().isEmpty()) {

                            Log.e(TAG, "openAddbookDialog: " + task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                participantDetailCell pdcell = new participantDetailCell();
                                pdcell.setCollege(String.valueOf(document.get("college")));
                                pdcell.setEmail(String.valueOf(document.get("email")));
                                pdcell.setEventid(String.valueOf(document.get("eventid")));
                                pdcell.setTeam_name(String.valueOf(document.get("team_name")));
                                pdcell.setTeam_rep(String.valueOf(document.get("team_rep")));
                                Log.e(TAG, "getData: "+document.get("team_rep") );
                                if (document.get("name") != null) {
                                    ArrayList<String> name = (ArrayList<String>) document.get("name");
                                    pdcell.setName(name);
                                }
                                if (document.get("contact") != null) {
                                    ArrayList<String> contact = (ArrayList<String>) document.get("contact");
                                    pdcell.setContact(contact);
                                }

                                Log.e(TAG, "onGetData()" + pdcell.getCollege());
                                listData.add(pdcell);
                            }
                        } else {
                            pt_rv.setVisibility(View.GONE);
                            none.setVisibility(View.VISIBLE);
                        }
                    }

                    ptadapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    loading.startShimmerAnimation();

                });
    }


    @Override
    public void onStart() {
        super.onStart();
        //ptadapter.startListening();

        if (!isDataAvailable) {
            loading.stopShimmerAnimation();
            loading.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.VISIBLE);
            loading.startShimmerAnimation();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        //ptadapter.stopListening();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isDataAvailable = true;
    }
}
