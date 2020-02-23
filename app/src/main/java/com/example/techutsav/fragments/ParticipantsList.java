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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        return view;
    }

    private void getData() {

        FirebaseFirestore db=FirebaseFirestore.getInstance();

        loading.startShimmerAnimation();
        db.collection("Participant")
                .whereEqualTo("aEventID", eventid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (!task.getResult().isEmpty()) {

                            Log.e(TAG, "openAddbookDialog: " + task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                participantDetailCell pdcell = new participantDetailCell();
                                pdcell.setCollege(String.valueOf(document.get("bCollege")));
                                pdcell.setEventID(String.valueOf(document.get("aEventID")));
                                Log.e(TAG, "Game: "+eventid);
                                if(!document.get("aTopic").equals(" NULL")){
                                    pdcell.setTopic(String.valueOf(document.get("aTopic")));
                                }
                                pdcell.setPhoneno(String.valueOf(document.get("cPhoneNo")));
                                pdcell.setEmail(String.valueOf(document.get("eEmail")));
                                pdcell.setParticipantName((ArrayList<String>) document.get("fParticipantName"));
                                Log.e(TAG, "getData: "+pdcell.getParticipantName());
                                pdcell.setParticipantRegno((ArrayList<String>) document.get("gParticipantRegno"));
                                pdcell.setParticipantDept((ArrayList<String>) document.get("hParticipantDept"));
                                pdcell.setBackupPhone(String.valueOf(document.get("dBackUpPhoneNo")));

                                Log.e(TAG, "Game"+ document.get("aGameID"));

                                if(document.get("aGameID").equals("game01"))
                                {
                                    pdcell.setSquadName(String.valueOf(document.get("aSquadName")));
                                    pdcell.setGame("PUBG");
                                }
                                else if(document.get("aGameID").equals("game02"))
                                {
                                    pdcell.setGame("Blur");
                                }
                                else if(document.get("aGameID").equals("game03"))
                                {
                                    pdcell.setGame("Counter Strike");
                                }

                                Log.e(TAG, "onGetData()" + pdcell.getCollege());
                                listData.add(pdcell);

                            }
                            Log.e(TAG, "getData: 00"+listData.size() );

                            pt_rv.setAdapter(ptadapter);
                            loading.setVisibility(View.GONE);
                            loading.stopShimmerAnimation();

                        } else {

                            loading.setVisibility(View.GONE);
                            loading.stopShimmerAnimation();
                            pt_rv.setVisibility(View.GONE);
                            none.setVisibility(View.VISIBLE);
                        }
                    }
                    ptadapter.notifyDataSetChanged();


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
