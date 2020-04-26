package com.example.fsh_noticet;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PINFragment extends Fragment {
    private EditText tPIN;
    private Button verifyButton;


    public PINFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tPIN=view.findViewById(R.id.PIN_ET);
        verifyButton=view.findViewById(R.id.verify_button);
        final NavController navController= Navigation.findNavController(view);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinC=tPIN.getText().toString().trim();
                if(pinC.equals("7777")){
                    navController.navigate(R.id.action_PINFragment_to_TLoginFragment);
                    Toast.makeText(getContext(),"Verified",Toast.LENGTH_SHORT).show();
                }
                else{
                    tPIN.setError("Please Enter the Correct PIN");
                    tPIN.setText("");
                }
            }
        });
    }
}
