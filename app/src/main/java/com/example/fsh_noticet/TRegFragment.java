package com.example.fsh_noticet;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class TRegFragment extends Fragment {
    private EditText tFullName,tDepartment,tPhone,tEmail,tPassword,tPassword2;
    private Button tRegisterButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    //private DocumentReference documentReference;
    private FirebaseFirestore fStore;
    private String uId;


    public TRegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treg, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tFullName=view.findViewById(R.id.t_full_name);
        tDepartment=view.findViewById(R.id.t_department);
        tPhone=view.findViewById(R.id.t_phone);
        tEmail=view.findViewById(R.id.t_email);
        tPassword=view.findViewById(R.id.t_password);
        tPassword2=view.findViewById(R.id.t_password2);
        tRegisterButton=view.findViewById(R.id.t_register_button);
        firebaseAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        final NavController navController= Navigation.findNavController(view);
        tRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tRegisterButton.setEnabled(false);
                final String fullName=tFullName.getText().toString().trim();
                final String department=tDepartment.getText().toString().trim();
                final String phone=tPhone.getText().toString().trim();
                final String email=tEmail.getText().toString().trim();
                String password=tPassword.getText().toString().trim();
                String password2=tPassword2.getText().toString().trim();
                if(TextUtils.isEmpty(fullName)){
                    tFullName.setError("Please enter your name");
                    tRegisterButton.setEnabled(true);
                } else if (TextUtils.isEmpty(department)) {
                    tDepartment.setError("Please enter course");
                    tRegisterButton.setEnabled(true);
                } else if (TextUtils.isEmpty(phone)) {
                    tPhone.setError("Please Enter Your Ph.No.");
                    tRegisterButton.setEnabled(true);
                } else if (TextUtils.isEmpty(email)) {
                    tEmail.setError("Please enter your email address");
                    tRegisterButton.setEnabled(true);
                } else if (TextUtils.isEmpty(password)) {
                    tPassword.setError("Please enter your password");
                    tRegisterButton.setEnabled(true);
                } else if (password.length() < 6) {
                    tPassword.setError("Password must be more than  or equal to 6 characters long");
                    tRegisterButton.setEnabled(true);
                } else if (TextUtils.isEmpty(password2)) {
                    tPassword2.setError("Please confirm your password");
                    tRegisterButton.setEnabled(true);
                } else if(!password.matches(password2)){
                    tPassword2.setError("Passwords do not match");
                    tRegisterButton.setEnabled(true);
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                uId=firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fStore.collection("Teachers").document(uId);
                                TeacherInfo teacherInfo=new TeacherInfo(fullName,department,phone,email);
                                documentReference.set(teacherInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Result:","Profile Created for "+uId);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Result:","Failed:"+e.toString());
                                    }
                                });
                                Toast.makeText(getContext(), "Registeration Successfull", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.action_TRegFragment_to_homeActivity);
                                getActivity().finish();
                            }
                            else{
                                tRegisterButton.setEnabled(true);
                                Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
