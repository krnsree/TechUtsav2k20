package com.example.techutsav.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.techutsav.models.AlumniList;
import com.example.techutsav.adapters.AlumniRecyclerViewAdapter;
import com.example.techutsav.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlumniFragment extends Fragment {

    private RecyclerView alumniRecycler;
    private AlumniRecyclerViewAdapter adapter;
    private ArrayList<AlumniList> alumniLists = new ArrayList<>();

    //Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Shimmer






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
        adapter = new AlumniRecyclerViewAdapter(getContext(),alumniLists,getActivity());
        alumniRecycler.setAdapter(adapter);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();

    }


    void getData(){

        db.collection("Alumni")
                .orderBy("alumniid", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){


                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){


                                if (documentSnapshot.exists()) {

                                    AlumniList dataList = new AlumniList();

                                    dataList.setDesignation(String.valueOf(documentSnapshot.getData().get("designation")));
                                    dataList.setImg(String.valueOf(documentSnapshot.getData().get("img")));
                                    dataList.setName(String.valueOf(documentSnapshot.getData().get("name")));
                                    dataList.setQuotes(String.valueOf(documentSnapshot.getData().get("quotes")));

                                    alumniLists.add(dataList);
                                    adapter.notifyDataSetChanged();
                                    Log.i("Alumni","received");
                                }
                            }
                        }
                    }
                });











    }


}
