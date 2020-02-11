package com.example.techutsav;

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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

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
    static ArrayList<String> cordListTeacherName = new ArrayList<>();
    static ArrayList<String> cordListTeacherDept = new ArrayList<>();

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
            }
        }

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                return true;
            }
            return false;
        });

        eventNameShimmer.startShimmerAnimation();
        descShimmer.startShimmerAnimation();
        topicShimmer.startShimmerAnimation();
        cordstuShimmer.startShimmerAnimation();
        cordteaShimmer.startShimmerAnimation();

        ptbtn.setOnClickListener(view1 -> {
            ParticipantsList pl = new ParticipantsList(listData.getEventId());
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                    , pl).addToBackStack(null).commitAllowingStateLoss();
        });

        resbtn.setOnClickListener(view12 -> {
                    ResultBtmDlg resultBtmDlg = ResultBtmDlg.newInstance(listData.getEventId());
                    resultBtmDlg.show(getActivity().getSupportFragmentManager(), "result_bottom_dialouge");
                }
        );

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

            getCordinatorStudent();
            getCordinatorTeacher();
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

        if (cordListStudentName != null && cordListStudentName.size() > 0) {
            putCordinData(cordListStudentName, cordListStudentDept, "s");
            cordstuShimmer.setVisibility(View.GONE);
            cordstuShimmer.stopShimmerAnimation();
            return;
        } else {
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
                                }
                            }
                            putCordinData(cordListStudentName, cordListStudentDept, "s");
                            cordstuShimmer.setVisibility(View.GONE);
                            cordstuShimmer.stopShimmerAnimation();
                        }
                    });

        }

    }

    private void getCordinatorTeacher() {

        if (cordListTeacherName != null && cordListTeacherName.size() > 0) {
            putCordinData(cordListTeacherName, cordListTeacherDept, "t");
            cordteaShimmer.stopShimmerAnimation();
            cordteaShimmer.setVisibility(View.GONE);
            return;
        } else {
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
                            putCordinData(cordListTeacherName, cordListTeacherDept, "t");
                            cordteaShimmer.stopShimmerAnimation();
                            cordteaShimmer.setVisibility(View.GONE);
                        }
                    });

        }
    }

    private void putCordinData(ArrayList<String> cordListName, ArrayList<String> cordListDept, String tag) {

        String str = " ";
        for (int i = 0; i < cordListName.size(); i++) {
            str = str + (i + 1) + ").\t" + cordListName.get(i) + "\t-\t" + cordListDept.get(i) + "\n";
            Log.e(TAG, "OnNames" + str);
        }

        if (tag.equals("s"))
            cordinatorListStudent.setText(str);
        else if (tag.equals("t"))
            cordinatorListTeacher.setText(str);

        putData();
    }
}