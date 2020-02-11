package com.example.techutsav;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlumniFragment extends Fragment {

    private RecyclerView alumniRecycler;
    private AlumniRecyclerViewAdapter adapter;
    private ArrayList<AlumniList> alumniLists;





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
        adapter = new AlumniRecyclerViewAdapter();
        alumniRecycler.setAdapter(adapter);


        return view;
    }

}
