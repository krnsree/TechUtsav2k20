package com.example.techutsav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;


public class passwordFragment extends BottomSheetDialogFragment {

    private static String passType;

    @BindView(R.id.password)
    TextView password;

    @BindView(R.id.cnfrmButton)
    Button confirm;

    String Password;
    private static String id;

    FirebaseFirestore ref;

    public passwordFragment() {
    }

    public static passwordFragment newInstance(String type,String eventid) {

        id=eventid;
        passType=type;
       return new passwordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_password, container, false);

        ButterKnife.bind(this,view);

        ref=FirebaseFirestore.getInstance();

        ref.collection("Password")
                .whereEqualTo("type",passType)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {

                        for(QueryDocumentSnapshot snapshot: task.getResult())
                        {
                            Log.e("TAG", "passcnf: here"+ snapshot.getData().get("password"));
                            Password=String.valueOf(snapshot.getData().get("password"));
                            Log.e("TAG", "passcnf: here"+ snapshot.getData().get("password"));
                        }
                    }


                });

        confirm.setOnClickListener(view1 -> passcnf());

        return view;
    }

    private void passcnf() {
        Log.e("Tag", "passcnf: "+Password );
        try {
            if (Password.equals(password.getText().toString())) {
                if (passType.equals("coordinators")){
                    ParticipantsList pl = new ParticipantsList(id);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                            , pl).addToBackStack(null).commitAllowingStateLoss();
                }

                else
                {
                    AddResult addResult=new AddResult(id);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame
                            , addResult).addToBackStack(null).commitAllowingStateLoss();
                }
                    dismiss();
            } else {
                Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.e("Tag", "passcnf: "+e.getLocalizedMessage() );
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
