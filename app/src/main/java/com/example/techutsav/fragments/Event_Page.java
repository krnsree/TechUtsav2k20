package com.example.techutsav.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.techutsav.models.EventDataCell;
import com.example.techutsav.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Event_Page extends Fragment {

    private static final String TAG = "";
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

    @BindView(R.id.participantbtn)
    Button ptbtn;

    @BindView(R.id.resultbtn)
    Button resbtn;


    @BindView(R.id.cordinator_list_student)
    TextView cordinatorListStudent;

    @BindView(R.id.cordinator_list_teacher)
    TextView cordinatorListTeacher;

    @BindView(R.id.descShimmer)
    ShimmerFrameLayout descShimmer;

    @BindView(R.id.topicShimmer)
    ShimmerFrameLayout topicShimmer;

    @BindView(R.id.cordstuShimmer)
    ShimmerFrameLayout cordstuShimmer;

    @BindView(R.id.cordTeaShimmer)
    ShimmerFrameLayout cordteaShimmer;

    @BindView(R.id.EventNameShimmer)
    ShimmerFrameLayout eventNameShimmer;


    private EventDataCell listData;

    FirebaseFirestore ref;

    Context context;
    FragmentActivity activity;

    static ArrayList<String> cordListStudentName = new ArrayList<>();
    static ArrayList<String> cordListStudentDept = new ArrayList<>();
    static ArrayList<Long> cordListStudentContact = new ArrayList<>();
    static ArrayList<String> cordListTeacherName = new ArrayList<>();
    static ArrayList<String> cordListTeacherDept = new ArrayList<>();
    private int position;

    public Event_Page(Context context1, FragmentActivity activity) {
        this.context = context1;
        this.activity = activity;
    }

    private Button registerBt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event__page, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            String list = getArguments().getString("EVENT_DATA");
            position = Integer.parseInt(getArguments().getString("POS"));
            Gson gson = new Gson();
            listData = gson.fromJson(list, EventDataCell.class);
            getCordinatorStudent();
            getCordinatorTeacher();
        }

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle(listData.getTitle());
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);

            }
        }

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        eventNameShimmer.startShimmerAnimation();
        descShimmer.startShimmerAnimation();
        topicShimmer.startShimmerAnimation();
        cordstuShimmer.startShimmerAnimation();
        cordteaShimmer.startShimmerAnimation();

        ptbtn.setOnClickListener(view1 -> {

            passwordFragment pf = passwordFragment.newInstance("coordinators", listData.getEventId());
            pf.show(getActivity().getSupportFragmentManager(), "coordinators");
        });

        resbtn.setOnClickListener(view12 -> {
                    ResultBtmDlg resultBtmDlg = ResultBtmDlg.newInstance(listData.getEventId(), getActivity().getSupportFragmentManager());
                    resultBtmDlg.show(getActivity().getSupportFragmentManager(), "result_bottom_dialouge");
                }
        );

        return view;

    }


    private void putData() {

        eventName.setText(listData.getName());
        eventDesc.setText(listData.getDescription());
        eventTime.setText(listData.getTime());
        Log.e(TAG, "putData: "+listData.getImageUrl() );

        if (position == 0){
            Glide.with(context)
                    .load(R.drawable.paper)
                    .placeholder(R.drawable.paper)
                    .into(eventPic);
        }else if (position == 1){
            Glide.with(context)
                    .load(R.drawable.poster)
                    .placeholder(R.drawable.poster)
                    .into(eventPic);
        }else if (position == 2){
            Glide.with(context)
                    .load(R.drawable.fotographia)
                    .placeholder(R.drawable.fotographia)
                    .into(eventPic);
        }else if (position == 3){
            Glide.with(context)
                    .load(R.drawable.mind_bender)
                    .placeholder(R.drawable.mind_bender)
                    .into(eventPic);
        }else if (position == 4){
            Glide.with(context)
                    .load(R.drawable.alpha)
                    .placeholder(R.drawable.alpha)
                    .into(eventPic);
        }else if (position == 5){
            Glide.with(context)
                    .load(R.drawable.bugbuster)
                    .placeholder(R.drawable.bugbuster)
                    .into(eventPic);
        }else if (position == 6){
            Glide.with(context)
                    .load(R.drawable.adzap)
                    .placeholder(R.drawable.adzap)
                    .into(eventPic);
        }else if (position == 7){
            Glide.with(context)
                    .load(R.drawable.troll)
                    .placeholder(R.drawable.troll)
                    .into(eventPic);
        }else if (position == 9){
            Glide.with(context)
                    .load(R.drawable.special)
                    .placeholder(R.drawable.special)
                    .into(eventPic);
        }else if (position == 10){
            Glide.with(context)
                    .load(R.drawable.cinephillia)
                    .placeholder(R.drawable.cinephillia)
                    .into(eventPic);
        }else if (position == 8){
            Glide.with(context)
                    .load(R.drawable.xpresso)
                    .placeholder(R.drawable.xpresso)
                    .into(eventPic);
        }

        if (listData.getTopic() != null) {
            topicsTitle.setVisibility(View.VISIBLE);
            String s = "";
            ArrayList<String> list = listData.getTopic();
            for (int i = 0; i < list.size(); i++) {
                s = s + (i + 1) + ".\t" + list.get(i) + "\n";
            }
            topics.setText(s);
        } else {
            topicsTitle.setVisibility(View.GONE);
            topics.setVisibility(View.GONE);
        }
        eventNameShimmer.setVisibility(View.GONE);
        eventNameShimmer.stopShimmerAnimation();
        descShimmer.setVisibility(View.GONE);
        descShimmer.startShimmerAnimation();
        topicShimmer.setVisibility(View.GONE);
        topicShimmer.stopShimmerAnimation();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void getCordinatorStudent() {

        Log.e(TAG, "getCordinate " + listData.getEventId());
        ref.collection("co-ordinator(student)")
                .whereEqualTo("eventid", listData.getEventId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                            if (documentSnapshot.exists()) {

                                cordListStudentName = (ArrayList<String>) documentSnapshot.getData().get("name");
                                cordListStudentDept = (ArrayList<String>) documentSnapshot.getData().get("department");
                                cordListStudentContact = (ArrayList<Long>) documentSnapshot.getData().get("contact");
                            }

                        }
                        putCordinData(cordListStudentName, cordListStudentDept, cordListStudentContact, "s");
                        cordstuShimmer.setVisibility(View.GONE);
                        cordstuShimmer.stopShimmerAnimation();
                    }
                });

        // }

    }

    private void getCordinatorTeacher() {

        Log.e(TAG, "getCordinate " + listData.getEventId());
        ref.collection("co-ordinator(admin)")
                .whereEqualTo("eventid", listData.getEventId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                            if (documentSnapshot.exists()) {

                                cordListTeacherName = (ArrayList<String>) documentSnapshot.getData().get("name");
                                cordListTeacherDept = (ArrayList<String>) documentSnapshot.getData().get("dept");

                            }
                        }
                        putCordinData(cordListTeacherName, cordListTeacherDept, null, "t");
                        cordteaShimmer.stopShimmerAnimation();
                        cordteaShimmer.setVisibility(View.GONE);
                    }
                });

        // }
    }

    private void putCordinData(ArrayList<String> cordListName, ArrayList<String> cordListDept, ArrayList<Long> cordListContact, String tag) {

        String str = " ";

        try {
            if (tag.equals("s")) {
                for (int i = 0; i < cordListName.size(); i++) {
                    str = str + (i + 1) + ").\t" + cordListName.get(i) + "\t\t-\t" + cordListDept.get(i) + " \t-\t" + String.valueOf(cordListContact.get(i)) + "\n";
                    Log.e(TAG, "OnNames" + str);
                }
                cordinatorListStudent.setText(str);
            } else if (tag.equals("t")) {
                for (int i = 0; i < cordListName.size(); i++) {
                    str = str + (i + 1) + ").\t" + cordListName.get(i) + "\t\t-\t" + cordListDept.get(i) + "\n";
                    Log.e(TAG, "OnNames" + str);
                }
                cordinatorListTeacher.setText(str);
            }
        }catch (Exception e){
            Log.e(TAG, "putCordinData: "+e.getLocalizedMessage() );
        }
        putData();
    }
}