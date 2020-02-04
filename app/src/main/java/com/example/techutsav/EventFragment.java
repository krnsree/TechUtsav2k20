package com.example.techutsav;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    ArrayList<EventDataCell> eventItems = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        bottomNavigationView=getActivity().findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setVisibility(view.VISIBLE);
        //RecyclerView
        eventsList = view.findViewById(R.id.event_list);
        eventsList.setHasFixedSize(true);
        eventsList.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        adapter = new EventRecyclerViewAdapter(eventItems, getContext(), getActivity());
        eventsList.setAdapter(adapter);

        pgbar=view.findViewById(R.id.rvprogressbar);

        //Action Bar

        eventActionBar(view);


        //Recycler View Data
        //isDataAvailable = false;


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //isDataAvailable = true;
        getData();

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

            case R.id.event_action_schedule:
                ((NavigationHost)getActivity()).navigateTo(new ScheduleFragment(), true);
                 return true;
            case R.id.event_action_info:
                ((NavigationHost)getActivity()).navigateTo(new InfoFragment(),true);
                return true;
            case R.id.event_action_registration:
                ((NavigationHost)getActivity()).navigateTo(new RegistrationFragment(), true);
                return  true;
        }

        return false;
    }


    private void getData() {

        ref = FirebaseFirestore.getInstance();

        if(eventItems!=null && eventItems.size()>0)
            eventItems.clear();

        ref.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if (documentSnapshot.exists()) {

                                    EventDataCell dataCell = new EventDataCell();
                                    dataCell.setName(String.valueOf(documentSnapshot.getData().get("Name")));
                                    dataCell.setDate(String.valueOf(documentSnapshot.getData().get("date")));
                                    dataCell.setDescription(String.valueOf(documentSnapshot.getData().get("description")));
                                    dataCell.setEventId(String.valueOf(documentSnapshot.getData().get("eventid")));
                                    dataCell.setImageUrl(String.valueOf(documentSnapshot.getData().get("image")));
                                    dataCell.setTime(String.valueOf(documentSnapshot.getData().get("time")));
                                    if (documentSnapshot.get("topic") != null) {
                                        ArrayList<String> list = (ArrayList<String>) documentSnapshot.get("topic");

                                        for(String item : list){

                                            Log.e(TAG, "onComplete: "+item);

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

                            adapter.notifyDataSetChanged();
                            pgbar.setVisibility(View.GONE);
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
        Log.e(TAG, "onPause: 3");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onPause: 2");

    }

    @Override
    public void onStart() {
        super.onStart();
        if(!isDataAvailable)
            pgbar.setVisibility(View.GONE);
        else
            pgbar.setVisibility(View.VISIBLE);
        Log.e(TAG, "onPause: 1");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isDataAvailable = true;
        Log.e(TAG, "onPause: 0");

    }


}
