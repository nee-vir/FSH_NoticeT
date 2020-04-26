package com.example.fsh_noticet;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class TLoginFragment extends Fragment {
    private EditText tEmail;
    private EditText tPassword;
    private Button loginButton;
    private Button regLink;
    private FirebaseAuth fAuth;



    public TLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tlogin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tEmail=view.findViewById(R.id.temail);
        tPassword=view.findViewById(R.id.tpassword);
        loginButton=view.findViewById(R.id.t_login_button);
        regLink=view.findViewById(R.id.reg_link_button);
        fAuth= FirebaseAuth.getInstance();
        final NavController navController= Navigation.findNavController(view);
        if(fAuth.getCurrentUser()!=null){
            navController.navigate(R.id.action_TLoginFragment_to_homeActivity);
            getActivity().finish();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                String lemail=tEmail.getText().toString().trim();
                String lpassword=tPassword.getText().toString().trim();
                if(TextUtils.isEmpty(lemail)){
                    tEmail.setError("Email is required");
                    loginButton.setEnabled(true);
                }
                else if(TextUtils.isEmpty(lpassword)){
                    tPassword.setError("Password is required");
                    loginButton.setEnabled(true);
                }
                else{
                    fAuth.signInWithEmailAndPassword(lemail,lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(),"Log in Successful",Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.action_TLoginFragment_to_homeActivity);
                                getActivity().finish();//to finish the fragment
                            }
                            else{
                                Toast.makeText(getActivity(),"Log in Failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                loginButton.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });
        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_TLoginFragment_to_TRegFragment);
            }
        });
    }
}
