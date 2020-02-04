package com.example.techutsav;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Event_Page extends Fragment {

    private static final String TAG ="" ;
    @BindView(R.id.event_name)
    TextView eventName;

    @BindView(R.id.event_time)
    TextView eventTime;

    @BindView(R.id.event_description)
    TextView eventDesc;

    @BindView(R.id.topics)
    TextView topics;

    @BindView(R.id.topic_title)
    TextView topicsTitle;

    @BindView(R.id.event_poster)
    ImageView eventPic;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.cordinator_list)
    TextView cordinatorList;
    private EventDataCell listData ;

    FirebaseFirestore ref;

    Context context;

    ArrayList<String> cordList=new ArrayList<>();

    public Event_Page(Context context) {
        this.context = context;
    }

    private Button registerBt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref=FirebaseFirestore.getInstance();
    }

    private void getCordinator() {

        Log.e(TAG,"getCordinate "+listData.getEventId());
        ref.collection("co-ordinator(student)")
                .whereEqualTo("eventid",listData.getEventId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if (documentSnapshot.exists()) {

                                    cordList= (ArrayList<String>) documentSnapshot.getData().get("name");
                                    for(String item : cordList){

                                        Log.e(TAG, "onCordinate: "+cordList.size());

                                    }

                                }
                            }
                            putCordinData();

                        }
                    }
                });

    }

    private void putCordinData() {

        String str="";
        for(int i=0;i<cordList.size();i++)
        {
            str=str+(i+1)+".\t"+ cordList.get(i) +"\n";
            Log.e(TAG,"OnNames"+str);
        }

        cordinatorList.setText(str);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event__page, container, false);
        ButterKnife.bind(this, view);



        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle("TechUtsav");
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }}

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setVisibility(view.GONE);

        if (getArguments() != null) {
            String list = getArguments().getString("EVENT_DATA");
            Gson gson = new Gson();
            listData = gson.fromJson(list, EventDataCell.class);
            // listData = gson.fromJson(list, new TypeToken<List<EventDataCell>>()
            // {}.getType());
            // Log.e(TAG, "onViewCreated: "+listData.getName() );

            getCordinator();
            putData();

        }

    }

    private void putData() {

        eventName.setText(listData.getName());
        eventDesc.setText(listData.getDescription());
        eventTime.setText(listData.getTime());
        Glide.with(context)
                .load(listData.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(eventPic);

        if(listData.getTopic()!=null)
        {
            topicsTitle.setVisibility(View.VISIBLE);
            String s="";
            ArrayList<String> list=listData.getTopic();
            for(int i=0;i<list.size();i++)
            {
                s=s+(i+1)+".\t"+ list.get(i) +"\n";
            }
            topics.setText(s);
        }
        else {
            topicsTitle.setVisibility(View.GONE);
            topics.setVisibility(View.GONE);
        }


    }


}
