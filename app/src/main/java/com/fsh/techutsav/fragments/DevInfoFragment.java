package com.fsh.techutsav.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fsh.techutsav.R;
import com.fsh.techutsav.adapters.DevInfoRecyclerViewAdapter;
import com.fsh.techutsav.models.DevDataCell;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DevInfoFragment extends Fragment {

    RecyclerView devRecycler;
    DevInfoRecyclerViewAdapter adapter;
    ArrayList<DevDataCell> dList = new ArrayList<>();

    FirebaseFirestore ref;

    final int dev_images[]={R.drawable.hari,R.drawable.kiran,R.drawable.nash,R.drawable.akanksha,R.drawable.sivani,R.drawable.motta,R.drawable.patla};
    final String dev_name[] = {"HariKrishnan", "Kiran", "Nash Jacob John", "Akanksha Shahi", "Sivani Rupvat", "Rahul R Y", "Rahul Raj"};
    final String dev_designation[] = {"Developer", "Developer", "Developer", "DataBase", "DataBase", "Designer", "Designer"};
    final String dev_dept[] = {"BCA", "BCA", "BSc Computer Science", "BCA", "BCA", "BCA", "BCA"};
    final String dev_year[] = {"3rd year", "3rd year", "3rd year", "3rd year", "3rd year", "2nd year", "3rd year"};


    public DevInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        ref = FirebaseFirestore.getInstance();

        devRecycler = view.findViewById(R.id.dev_recycler);
        devRecycler.setHasFixedSize(true);
        devRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        dList.addAll(getDevData());
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.GONE);
        adapter = new DevInfoRecyclerViewAdapter(getContext(), dList, getActivity());
        devRecycler.setAdapter(adapter);


        return view;


    }

    ArrayList<DevDataCell> getDevData() {

        ArrayList<DevDataCell> devList = new ArrayList<>();
        for (int i = 0; i < dev_name.length; i++) {

            DevDataCell cell = new DevDataCell();
            //cell.setImg(dev_img[i]);
            cell.setName(dev_name[i]);
            cell.setDesination(dev_designation[i]);
            cell.setDept(dev_dept[i]);
            cell.setYear(dev_year[i]);
            cell.setImg(dev_images[i]);
            devList.add(cell);
            Log.i("DevInfo", "Setting");
        }
        return devList;
    }

    @Override
    public void onStop() {
        super.onStop();

        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.VISIBLE);
    }
}
