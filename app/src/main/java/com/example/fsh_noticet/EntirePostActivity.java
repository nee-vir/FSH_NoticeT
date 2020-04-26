package com.example.fsh_noticet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntirePostActivity extends AppCompatActivity {
    private TextView entireTitle;
    private TextView entireBody;
    private ImageView entireImage;
    private List<Upload> uploads;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entire_post);
        Intent intent=getIntent();
        final int pos=intent.getIntExtra("itemPos",0);
        //String posS= Integer.toString(pos);
        entireTitle=findViewById(R.id.entire_title);
        entireBody=findViewById(R.id.entire_body);
        //entireBody.setText(posS);
        entireImage=findViewById(R.id.entire_image);
        uploads=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload=postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                Collections.reverse(uploads);
                //final List<Upload> finalUpload=new ArrayList<>(uploads);

                entireTitle.setText(uploads.get(pos).getTitle());
                entireBody.setText(uploads.get(pos).getBody());
                String postImageUrl=uploads.get(pos).getPostImageUrl();
                //Uri imageUri=Uri.parse(postImageUrl);
                if(!postImageUrl.equals("No Image")) {
                    Log.d("Testing:", "onDataChange: Working");
                    Picasso.get().load(uploads.get(pos).getPostImageUrl()).into(entireImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EntirePostActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
