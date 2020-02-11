package com.example.techutsav;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private RecyclerView galleryRecycler;
    private GalleryRecyclerViewAdapter adapter;
    ArrayList<GalleryList> galleryList = new ArrayList<>();
    FirebaseFirestore gRef;






    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryRecycler = view.findViewById(R.id.gallery_recycler_view);
        galleryRecycler.setHasFixedSize(true);
        galleryRecycler.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        adapter = new GalleryRecyclerViewAdapter(galleryList,getContext(),getActivity());
        galleryRecycler.setAdapter(adapter);

        //getGalleryData();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private void getGalleryData() {



        gRef = FirebaseFirestore.getInstance();

        gRef.collection("Gallery")
                .get()
                .addOnCompleteListener(task -> {
                     if(task.isSuccessful()){

                         Log.e(TAG, "onGallery: here");

                         for(QueryDocumentSnapshot documentSnapshots : task.getResult()){


                             if(documentSnapshots.exists()){

                                 GalleryList gList = new GalleryList();
                                 gList.setgImage(String.valueOf(documentSnapshots.getData().get("url")));
                                 galleryList.add(gList);
                             }
                         }
                         adapter.notifyDataSetChanged();
                     }else{
                         Toast.makeText(getActivity(), "asdasdsad", Toast.LENGTH_SHORT).show();
                     }
                });
           }

       }
