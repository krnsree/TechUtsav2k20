package com.fsh.techutsav.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;

import com.fsh.techutsav.models.EventDataCell;
import com.fsh.techutsav.adapters.EventRecyclerViewAdapter;
import com.fsh.techutsav.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {


    private boolean isDataAvailable = false;

    public EventFragment() {
        // Required empty public constructor
    }


    private RecyclerView eventsList;
    private EventRecyclerViewAdapter adapter;
    private FirebaseFirestore ref;
    private ProgressBar pgbar;
    private BottomNavigationView bottomNavigationView;
    //ListAdapter
    private static ArrayList<EventDataCell> eventItems = new ArrayList<>();

    ShimmerFrameLayout shimmerFrameLayout;

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
       /* bottomNavigationView = getActivity().findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setVisibility(view.VISIBLE);
*/
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.GONE);
        //RecyclerView
        eventsList = view.findViewById(R.id.event_list);
        eventsList.setHasFixedSize(true);
        eventsList.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        adapter = new EventRecyclerViewAdapter(eventItems, getContext(), getActivity());
        eventsList.setAdapter(adapter);

        shimmerFrameLayout = view.findViewById(R.id.parentShimmerLayout);

        //Action Bar
        eventActionBar(view);

        collapsingToolbar = view.findViewById(R.id.collapsing_toolbar_ev);
        toolbar = view.findViewById(R.id.event_action_bar);

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle("TechUtsav");
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


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onPause: 5");
        eventItems.clear();
        getData();

        //isDataAvailable = true;
        /*shimmerFrameLayout=getActivity().findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();*/

    }

    private void eventActionBar(View view) {

        Toolbar toolbar = view.findViewById(R.id.event_action_bar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shimmerFrameLayout.startShimmerAnimation();

        /*if (isDataAvailable){
            pgbar.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.event_acton_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.event_action_info:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                        , new DevInfoFragment()).addToBackStack(null).commitAllowingStateLoss();
//                ((NavigationHost) getActivity()).navigateTo(new DevInfoFragment(), true);
                return true;
            /*case R.id.event_action_registration:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                        , new RegistrationFragment()).addToBackStack(null).commitAllowingStateLoss();
                //((NavigationHost) getActivity()).navigateTo(new RegistrationFragment(), true);
                return true;*/
            case R.id.generalRules:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                        , new GeneralRulesFragment()).addToBackStack(null).commitAllowingStateLoss();
                // ((NavigationHost) getActivity()).navigateTo(new GeneralRulesFragment(), true);
                return true;
        }

        return false;
    }


    private void getData() {

        ref = FirebaseFirestore.getInstance();
        ref.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if (documentSnapshot.exists()) {

                                    Log.e(TAG, "onComplete: here");

                                    EventDataCell dataCell = new EventDataCell();
                                    dataCell.setName(String.valueOf(documentSnapshot.getData().get("Name")));
                                    dataCell.setDate(String.valueOf(documentSnapshot.getData().get("date")));
                                    dataCell.setVenue(String.valueOf(documentSnapshot.getData().get("Venue")));
                                    dataCell.setDescription(String.valueOf(documentSnapshot.getData().get("description")));
                                    dataCell.setEventId(String.valueOf(documentSnapshot.getData().get("eventid")));
                                    dataCell.setTime(String.valueOf(documentSnapshot.getData().get("time")));
                                    dataCell.setTitle(String.valueOf(documentSnapshot.getData().get("title")));
                                    if (documentSnapshot.get("topic") != null) {
                                        ArrayList<String> list = (ArrayList<String>) documentSnapshot.get("topic");

                                        for (String item : list) {

                                            Log.e(TAG, "onComplete: " + item);

                                        }
                                        dataCell.setTopic(list);
                                        /*for (String item : list) {
                                            Log.e(TAG, "onComplete: "+ Collections.singletonList(item));
                                            dataCell.setTopic(Collections.singletonList(item));
                                            Log.e(TAG, "onComplete: "+ dataCell.getTopic());
                                        }*/
                                    }

                                    eventItems.add(dataCell);
                                }
                            }
                            Log.e(TAG, "onComplete: " + eventItems.size());
                            adapter.notifyDataSetChanged();
                            shimmerFrameLayout.stopShimmerAnimation();
                            shimmerFrameLayout.setVisibility(View.GONE);

                        }


                    }
                });


    }

    @Override
    public void onPause() {
        super.onPause();
        //pgbar.setVisibility(View.GONE);
        Log.e(TAG, "onPause: 4");
        isDataAvailable = false;
        //Log.e(TAG, "onPause: "+pgbar.getVisibility());
    }

    @Override
    public void onResume() {
        super.onResume();
        //pgbar.setVisibility(View.GONE);

        Log.e("TAG", "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.VISIBLE);
        Log.e("TAG", "onStop: ");
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!isDataAvailable) {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        } else {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmerAnimation();
        }
        Log.e("TAG", "onStart: ");
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isDataAvailable = true;

        Log.e("TAG", "onAttach: ");
    }

}
