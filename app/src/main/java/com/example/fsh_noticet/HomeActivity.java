package com.example.fsh_noticet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        NavController navController= Navigation.findNavController(this,R.id.home_host_fragment);
        BottomNavigationView homeBotNav=findViewById(R.id.home_bot_nav);
        NavigationUI.setupWithNavController(homeBotNav,navController);
    }
}
