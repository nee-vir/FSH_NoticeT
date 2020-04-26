package com.example.fsh_noticet;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView pUsername,pDept,pPhone,pEmail;
    private Button pLogout;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private String uId;
    private DocumentReference docRef;
    private Dialog dialog;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pUsername=view.findViewById(R.id.user_name_text);
        pDept=view.findViewById(R.id.p_department);
        pPhone=view.findViewById(R.id.p_phone);
        pEmail=view.findViewById(R.id.p_email);
        pLogout=view.findViewById(R.id.p_logout);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        uId=fAuth.getCurrentUser().getUid();
        docRef=fStore.collection("Teachers").document(uId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                pUsername.setText(teacherInfo.getFullName());
                pDept.setText(teacherInfo.getDepartment());
                pPhone.setText(teacherInfo.getPhone());
                pEmail.setText(teacherInfo.getEmail());
            }
        });
        pLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button yesButton, noButton;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.logout_confirm);
                yesButton = dialog.findViewById(R.id.yes_button);
                noButton = dialog.findViewById(R.id.no_button);
                dialog.show();
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fAuth.signOut();
                        Intent intent=new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
