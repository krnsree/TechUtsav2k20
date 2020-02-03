package com.example.techutsav;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {


    public EventFragment() {
        // Required empty public constructor
    }


    private RecyclerView eventsList;
    private EventRecyclerViewAdapter adapter;
    private FirebaseFirestore ref;

    //ListAdapter
    ArrayList<EventDataCell> eventItems = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        //RecyclerView
        eventsList = view.findViewById(R.id.event_list);
        eventsList.setHasFixedSize(true);
        eventsList.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        adapter = new EventRecyclerViewAdapter(eventItems, getContext());
        eventsList.setAdapter(adapter);


        //Action Bar
        eventActionBar(view);


        //Recycler View Data
        getData();


        return view;
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.event_acton_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.event_action_schedule:
                break;
            case R.id.event_action_info:
                break;

        }


        return super.onOptionsItemSelected(item);
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

                                    EventDataCell dataCell = new EventDataCell();
                                    dataCell.setName(String.valueOf(documentSnapshot.getData().get("Name")));
                                    dataCell.setDate(String.valueOf(documentSnapshot.getData().get("date")));
                                    dataCell.setDescription(String.valueOf(documentSnapshot.getData().get("description")));
                                    dataCell.setEventId(String.valueOf(documentSnapshot.getData().get("eventid")));
                                    dataCell.setImageUrl(String.valueOf(documentSnapshot.getData().get("image")));
                                    dataCell.setTime(String.valueOf(documentSnapshot.getData().get("time")));
                                    if (documentSnapshot.get("topic") != null) {
                                        List<String> list = (List<String>) documentSnapshot.get("topic");
                                        for (String item : list) {
                                            dataCell.setTopic(Collections.singletonList(item));

                                        }
                                    }

                                    eventItems.add(dataCell);
                                }


                            }

                            adapter.notifyDataSetChanged();

                        }


                    }
                });


    }


}
