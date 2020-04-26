package com.example.fsh_noticet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fsh_noticet.write_post.BioTechWP;
import com.example.fsh_noticet.write_post.CommonWP;
import com.example.fsh_noticet.write_post.ComputerApplicationsWP;
import com.example.fsh_noticet.write_post.ComputerScienceWP;
import com.example.fsh_noticet.write_post.LanguageWP;
import com.example.fsh_noticet.write_post.MathsWP;
import com.example.fsh_noticet.write_post.VisComWP;

public class PostCategory extends AppCompatActivity {
    private Button cApp,cScience,visCom,maths,bioTech,language,commonPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_category);
        commonPost=findViewById(R.id.category_0);
        cApp=findViewById(R.id.category_1);
        cScience=findViewById(R.id.category_6);
        visCom=findViewById(R.id.category_2);
        maths=findViewById(R.id.category_3);
        bioTech=findViewById(R.id.category_4);
        language=findViewById(R.id.category_5);
        cApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ComputerApplicationsWP.class);
                startActivity(intent);
            }
        });
        cScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ComputerScienceWP.class);
                startActivity(intent);
            }
        });
        visCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), VisComWP.class);
                startActivity(intent);
            }
        });
        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MathsWP.class);
                startActivity(intent);
            }
        });
        bioTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), BioTechWP.class);
                startActivity(intent);
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LanguageWP.class);
                startActivity(intent);
            }
        });
        commonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CommonWP.class);
                startActivity(intent);
            }
        });
    }
}
