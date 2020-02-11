package com.example.techutsav;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;





/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private RecyclerView galleryRecycler;
    private GalleryRecyclerViewAdapter adapter;
    ArrayList<GalleryList> galleryList;
    ImageView galleryImages;





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
        galleryRecycler.setLayoutManager(new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false));
        adapter = new GalleryRecyclerViewAdapter(galleryList,getContext(),getActivity());
        galleryRecycler.setAdapter(adapter);


        return view;
    }

}
