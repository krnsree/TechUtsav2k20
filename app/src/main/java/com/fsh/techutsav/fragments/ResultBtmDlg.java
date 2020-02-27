package com.fsh.techutsav.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fsh.techutsav.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ResultBtmDlg extends BottomSheetDialogFragment {

    private static String eventid;

    @BindView(R.id.final_results1)
    TextView finalResults1;
    @BindView(R.id.finalResultShimmer)
    ShimmerFrameLayout finalshimmer;

    String result;

    @BindView(R.id.addrslyBttn)
    Button addresultButton;

    private static FragmentManager fractivity;


    FirebaseFirestore ref;



    public static ResultBtmDlg newInstance(String eventId, FragmentManager factivity) {

        fractivity = factivity;
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
        finalshimmer.startShimmerAnimation();

        getFinalsData();

        return view;

    }


    private void getFinalsData() {

        DocumentReference doc=ref.collection("Result").document(eventid);

        result="Results not decided";

        doc.get().addOnSuccessListener(documentSnapshot -> {
           if (documentSnapshot!=null){
               finalshimmer.stopShimmerAnimation();
               finalshimmer.setVisibility(View.GONE);
               result = "First Position:";
               ArrayList<String> members=new ArrayList<>();
               Log.e(TAG, "getFinalsData: "+documentSnapshot.getData() );
               if(documentSnapshot.get("one")!=null)
               {
                   members= (ArrayList<String>) documentSnapshot.getData().get("one");
                   for (String name : members){
                       result+="\n"+name;
                   }
               }

               if(documentSnapshot.get("two")!=null)
               {
                   result+="\n"+"Second Position:";
                   members= (ArrayList<String>) documentSnapshot.getData().get("two");
                   for (String name : members){
                       result+="\n"+name;
                   }
                   finalResults1.setText(result);
               }


               if(documentSnapshot.get("three")!=null)
               {
                   result+="\n"+"Second Position:";
                   members= (ArrayList<String>) documentSnapshot.getData().get("two");
                   for (String name : members){
                       result+="\n"+name;
                   }
                   finalResults1.setText(result);
               }
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


