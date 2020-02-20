package com.example.techutsav.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.techutsav.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {



    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.VISIBLE);
        Log.e("TAG", "onStop: " );
    }

}
