package com.fsh.techutsav.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.fsh.techutsav.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ResultBtmDlg extends BottomSheetDialogFragment {

    private static String eventid;
    @BindView(R.id.prelims_result)
    TextView prelimsResults;

    @BindView(R.id.final_results)
    TextView finalResults;

    @BindView(R.id.finalResultShimmer)
    ShimmerFrameLayout finalshimmer;

    @BindView(R.id.prelimsShimmerLayout)
    ShimmerFrameLayout prelimsshimmer;

    @BindView(R.id.prelims_title)
    TextView prelimsTitle;

    @BindView(R.id.addrslyBttn)
    Button addresultButton;

    private static FragmentManager fractivity;

    FirebaseFirestore ref;

    ArrayList<String> finals_regno = new ArrayList<>();
    ArrayList<String> finals_dept = new ArrayList<>();
    ArrayList<String> finals_teamname = new ArrayList<>();
    boolean rf;

    ArrayList<String> prelims_teamname = new ArrayList<>();
    ArrayList<String> prelims_dept = new ArrayList<>();
    ArrayList<String> prelims_regno = new ArrayList<>();
    boolean pf;


    public static ResultBtmDlg newInstance(String eventId, FragmentManager factivity) {

        fractivity=factivity;
        eventid = eventId;
        return new ResultBtmDlg();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.result_dialouge, container, false);

        ButterKnife.bind(this, view);
        ref = FirebaseFirestore.getInstance();
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.GONE);
        if (!(eventid.equals("eve004") || eventid.equals("eve006"))) {
            prelimsshimmer.setVisibility(View.GONE);
            prelimsResults.setVisibility(View.GONE);
            prelimsTitle.setVisibility(View.GONE);
        }

        else {
            prelimsshimmer.startShimmerAnimation();
            getPrelimsData();
        }
        finalshimmer.startShimmerAnimation();

        getFinalsData();
        setResults(finals_regno, finals_dept, finals_teamname, "r");

        addresultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAR();
            }
        });

        return view;

    }

    private void showAR() {

        passwordFragment pf=passwordFragment.newInstance("result",eventid);
        pf.show(getActivity().getSupportFragmentManager(),"coordinators");

        dismiss();

    }

    private void setResults(ArrayList<String> dept, ArrayList<String> regno, ArrayList<String> teamname, String r) {

        String results = "";

        Log.e(TAG, "setResults: " + rf);

        for (int i = 0; i < teamname.size(); i++) {
            results = results + (i + 1) + "). " + teamname.get(i) + " -" + dept.get(i) + " -" + regno.get(i) + "\n";
            Log.e(TAG, "setResults: " + results);
        }

        if (r.equals("p"))
            prelimsResults.setText(results);
        else if (r.equals("f"))
            finalResults.setText(results);

        addresultButton.setVisibility(View.VISIBLE);
    }

    private void setResults(String p) {

        String results = "Results are not decided. Sorry for the inconvenience";

        if (p.equals("p"))
            prelimsResults.setText(results);
        else if (p.equals("f"))
            finalResults.setText(results);

        addresultButton.setVisibility(View.VISIBLE);

    }

    private void getPrelimsData() {

        ref.collection("prelims_result")
                .whereEqualTo("eventid", eventid)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    prelims_dept = (ArrayList<String>) document.getData().get("dept");
                                    prelims_teamname = (ArrayList<String>) document.getData().get("team_name");
                                    prelims_regno = (ArrayList<String>) document.getData().get("regno");
                                    rf = true;
                                }
                            }
                            setResults(prelims_dept, prelims_regno, prelims_teamname, "p");
                        } else {
                            rf = false;
                            setResults("p");
                        }
                        prelimsshimmer.setVisibility(View.GONE);
                        prelimsshimmer.stopShimmerAnimation();
                    }

                });

    }

    private void getFinalsData() {

        ref.collection("Result")
                .whereEqualTo("eventid", eventid)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    finals_dept = (ArrayList<String>) document.getData().get("dept");
                                    finals_teamname = (ArrayList<String>) document.getData().get("team_name");
                                    finals_regno = (ArrayList<String>) document.getData().get("regno");
                                    Log.e(TAG, "getFinalsData: " + document.getData().get("regno"));

                                }
                            }
                            setResults(finals_dept, finals_regno, finals_teamname, "f");
                        } else {
                            pf = false;
                            Log.e(TAG, "getFinalsData: yher ");
                            setResults("f");

                        }
                        finalshimmer.setVisibility(View.GONE);
                        finalshimmer.stopShimmerAnimation();
                    }

                });

    }

    @Override
    public void onStop() {
        super.onStop();
        LinearLayout cards = getActivity().findViewById(R.id.cards);
        cards.setVisibility(View.GONE);
    }
}


