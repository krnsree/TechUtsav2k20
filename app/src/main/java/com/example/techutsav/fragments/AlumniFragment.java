package com.example.techutsav.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techutsav.R;
import com.example.techutsav.adapters.AlumniRecyclerViewAdapter;
import com.example.techutsav.models.AlumniList;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlumniFragment extends Fragment {

    private boolean isDataAvailable = true;

    private RecyclerView alumniRecycler;
    private AlumniRecyclerViewAdapter adapter;
    private static ArrayList<AlumniList> alumniLists = new ArrayList<>();

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;

    //Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Shimmer
    ShimmerFrameLayout shimmerFrameLayout;


    public AlumniFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //RecyclerView
        View view = inflater.inflate(R.layout.fragment_alumni, container, false);
        alumniRecycler = view.findViewById(R.id.alumni_recycler_view);
        alumniRecycler.setHasFixedSize(true);
        alumniRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AlumniRecyclerViewAdapter(getContext(), alumniLists, getActivity());
        alumniRecycler.setAdapter(adapter);

        shimmerFrameLayout = view.findViewById(R.id.alumniShimmerLayout);
        collapsingToolbar=view.findViewById(R.id.collapsing_toolbar_al);
        toolbar=view.findViewById(R.id.event_action_bar_al);

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle("Alumni");
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
            }
        }

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        getData();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    void getData() {

        shimmerFrameLayout.startShimmerAnimation();

        if (alumniLists != null && alumniLists.size() > 0) {

            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            isDataAvailable = false;
            return;
        }

        db.collection("Alumni")
                .orderBy("alumniid", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {


                                if (documentSnapshot.exists()) {

                                    AlumniList dataList = new AlumniList();

                                    dataList.setDesignation(String.valueOf(documentSnapshot.getData().get("designation")));
                                    dataList.setImg(String.valueOf(documentSnapshot.getData().get("img")));
                                    dataList.setName(String.valueOf(documentSnapshot.getData().get("name")));
                                    dataList.setQuotes(String.valueOf(documentSnapshot.getData().get("quotes")));

                                    isDataAvailable=false;
                                    alumniLists.add(dataList);
                                    adapter.notifyDataSetChanged();
                                    shimmerFrameLayout.setVisibility(View.GONE);
                                    shimmerFrameLayout.stopShimmerAnimation();
                                    Log.i("Alumni", "received");
                                }
                            }
                        }
                    }
                });


    }


    @Override
    public void onStart() {
        super.onStart();
        if (!isDataAvailable) {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
        else {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmerAnimation();
        }
        Log.e(TAG, "onPause: 1");

    }
}
