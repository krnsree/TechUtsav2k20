package com.example.techutsav;


import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;




/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private RecyclerView galleryRecycler;
    private GalleryRecyclerViewAdapter adapter;

    private final int img[] = {

            R.drawable.img,
            R.drawable.img1,
            R.drawable.img11,
            R.drawable.img18,
            R.drawable.img2,
            R.drawable.img20,
            R.drawable.img21,
            R.drawable.img28,
            R.drawable.img29,
            R.drawable.img3,
            R.drawable.img30,
            R.drawable.img32,
            R.drawable.img33,
            R.drawable.img34,
            R.drawable.img37,
            R.drawable.img4,
            R.drawable.img40,
            R.drawable.img41,
            R.drawable.img45,
            R.drawable.img5,
            R.drawable.img67,
    };






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


        getGalleryData();


        ArrayList<GalleryList> gList = getGalleryData();
        adapter = new GalleryRecyclerViewAdapter(getContext(), gList);
        galleryRecycler.setAdapter(adapter);

        return view;
    }


    private ArrayList<GalleryList> getGalleryData() {

        ArrayList<GalleryList> image = new ArrayList<>();

        for(int i = 0; i < img.length; i++){

            GalleryList ls = new GalleryList();
            ls.setgImagge(img[i]);
            image.add(ls);
        }


        return image;
    }




       }
