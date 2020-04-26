package com.example.fsh_noticet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fsh_noticet.entire_posts.EntirePosts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntireMyPostActivity extends AppCompatActivity {
    private TextView title, body;
    private ImageView image1, image2, image3, image4, image5;
    private DatabaseReference databaseReference;
    private DatabaseReference dataRef;
    private DatabaseReference dRef;
    private Dialog dialog;
    private Toolbar cToolbar;
    private FirebaseAuth fAuth;
    private String uId;
    private String pCategory;
    private String uniquePostID;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit: {
                final EditText eTitle, eBody;
                final Button updateButton;
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.edit_post_layout);
                eTitle = dialog.findViewById(R.id.edit_title);
                eBody = dialog.findViewById(R.id.edit_body);
                updateButton = dialog.findViewById(R.id.update_button);
                dialog.show();
                switch (pCategory) {
                    case "CA": {
                        dataRef = FirebaseDatabase.getInstance().getReference("COMPUTER_APPLICATIONS_POSTS").child(uniquePostID);
                        break;
                    }
                    case "CS": {
                        dataRef = FirebaseDatabase.getInstance().getReference("COMPUTER_SCIENCE_POSTS").child(uniquePostID);
                        break;
                    }
                    case "LG": {
                        dataRef = FirebaseDatabase.getInstance().getReference("LANGUAGE_POSTS").child(uniquePostID);
                        break;
                    }
                    case "BT": {
                        dataRef = FirebaseDatabase.getInstance().getReference("BIOTECH_POSTS").child(uniquePostID);
                        break;
                    }
                    case "MA": {
                        dataRef = FirebaseDatabase.getInstance().getReference("MATHS_POSTS").child(uniquePostID);
                        break;
                    }
                    case "VC": {
                        dataRef = FirebaseDatabase.getInstance().getReference("VISCOM_POSTS").child(uniquePostID);
                        break;
                    }
                    case "CP": {
                        dataRef = FirebaseDatabase.getInstance().getReference("COMMON_POSTS").child(uniquePostID);
                        break;
                    }
                }
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        final DepartmentPost departmentPost = dataSnapshot.getValue(DepartmentPost.class);
                        eTitle.setText(departmentPost.getPostTitle());
                        eBody.setText(departmentPost.getPostBody());
                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateButton.setEnabled(false);
                                Toast.makeText(EntireMyPostActivity.this, "It may take some time to reflect the changes.", Toast.LENGTH_LONG).show();
                                departmentPost.setPostTitle(eTitle.getText().toString());
                                departmentPost.setPostBody(eBody.getText().toString());
                                databaseReference.setValue(departmentPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(EntireMyPostActivity.this, "Please Wait...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dataRef.setValue(departmentPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                    }
                                });

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(EntireMyPostActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(this, "Edit Post", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.item_delete: {
                Button yesButton, noButton;
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.delete_confirm);
                yesButton = dialog.findViewById(R.id.yes_button);
                noButton = dialog.findViewById(R.id.no_button);
                dialog.show();
                switch (pCategory) {
                    case "CA": {
                        dRef = FirebaseDatabase.getInstance().getReference("COMPUTER_APPLICATIONS_POSTS").child(uniquePostID);
                        break;
                    }
                    case "CS": {
                        dRef = FirebaseDatabase.getInstance().getReference("COMPUTER_SCIENCE_POSTS").child(uniquePostID);
                        break;
                    }
                    case "LG": {
                        dRef = FirebaseDatabase.getInstance().getReference("LANGUAGE_POSTS").child(uniquePostID);
                        break;
                    }
                    case "BT": {
                        dRef = FirebaseDatabase.getInstance().getReference("BIOTECH_POSTS").child(uniquePostID);
                        break;
                    }
                    case "MA": {
                        dRef = FirebaseDatabase.getInstance().getReference("MATHS_POSTS").child(uniquePostID);
                        break;
                    }
                    case "VC": {
                        dRef = FirebaseDatabase.getInstance().getReference("VISCOM_POSTS").child(uniquePostID);
                        break;
                    }
                    case "CP": {
                        dRef = FirebaseDatabase.getInstance().getReference("COMMON_POSTS").child(uniquePostID);
                        break;
                    }
                }
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EntireMyPostActivity.this,"Please Wait....",Toast.LENGTH_SHORT);
                            }
                        });
                        dRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                    }
                });

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //delete();
                //finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entire_my_post);
        cToolbar = findViewById(R.id.c_toolbar);
        setSupportActionBar(cToolbar);
        fAuth = FirebaseAuth.getInstance();
        uId = fAuth.getCurrentUser().getUid();
        dialog = new Dialog(this);
        title = findViewById(R.id.emp_title);
        body = findViewById(R.id.emp_body);
        image1 = findViewById(R.id.image_1);
        image2 = findViewById(R.id.image_2);
        image3 = findViewById(R.id.image_3);
        image4 = findViewById(R.id.image_4);
        image5 = findViewById(R.id.image_5);
        image1.setVisibility(View.GONE);
        image2.setVisibility(View.GONE);
        image3.setVisibility(View.GONE);
        image4.setVisibility(View.GONE);
        image5.setVisibility(View.GONE);
        Intent intent = getIntent();
        uniquePostID = intent.getStringExtra("UniqueID");
        pCategory = intent.getStringExtra("Category");
        databaseReference = FirebaseDatabase.getInstance().getReference("MY_POSTS").child(uId).child(uniquePostID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DepartmentPost departmentPost = dataSnapshot.getValue(DepartmentPost.class);
                title.setText(departmentPost.getPostTitle());
                body.setText(departmentPost.getPostBody());
                final String url1 = departmentPost.getImageUrl1();
                final String url2 = departmentPost.getImageUrl2();
                final String url3 = departmentPost.getImageUrl3();
                final String url4 = departmentPost.getImageUrl4();
                final String url5 = departmentPost.getImageUrl5();
                if (!url1.equals("")) {
                    Glide.with(EntireMyPostActivity.this)
                            .asBitmap()
                            .load(url1)
                            .centerCrop()
                            .into(image1);
                    image1.setVisibility(View.VISIBLE);
                }
                if (!url2.equals("")) {
                    Glide.with(EntireMyPostActivity.this)
                            .asBitmap()
                            .load(url2)
                            .centerCrop()
                            .into(image2);
                    image2.setVisibility(View.VISIBLE);
                }
                if (!url3.equals("")) {
                    Glide.with(EntireMyPostActivity.this)
                            .asBitmap()
                            .load(url3)
                            .centerCrop()
                            .into(image3);
                    image3.setVisibility(View.VISIBLE);
                }
                if (!url4.equals("")) {
                    Glide.with(EntireMyPostActivity.this)
                            .asBitmap()
                            .load(url4)
                            .centerCrop()
                            .into(image4);
                    image4.setVisibility(View.VISIBLE);
                }
                if (!url5.equals("")) {
                    Glide.with(EntireMyPostActivity.this)
                            .asBitmap()
                            .load(url5)
                            .centerCrop()
                            .into(image5);
                    image5.setVisibility(View.VISIBLE);
                }
                image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView fImage1;
                        Button cButton;
                        dialog.setContentView(R.layout.full_screen_image);
                        fImage1 = dialog.findViewById(R.id.image_fs);
                        cButton = dialog.findViewById(R.id.close_fs);
                        Glide.with(EntireMyPostActivity.this)
                                .asBitmap().fitCenter().load(url1).into(fImage1);
                        dialog.show();
                        cButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView fImage;
                        Button cButton;
                        dialog.setContentView(R.layout.full_screen_image);
                        fImage = dialog.findViewById(R.id.image_fs);
                        cButton = dialog.findViewById(R.id.close_fs);
                        Glide.with(EntireMyPostActivity.this).asBitmap().fitCenter().load(url2).into(fImage);
                        dialog.show();
                        cButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView fImage;
                        Button cButton;
                        dialog.setContentView(R.layout.full_screen_image);
                        fImage = dialog.findViewById(R.id.image_fs);
                        cButton = dialog.findViewById(R.id.close_fs);
                        Glide.with(EntireMyPostActivity.this).asBitmap().fitCenter().load(url3).into(fImage);
                        dialog.show();
                        cButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                image4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView fImage;
                        Button cButton;
                        dialog.setContentView(R.layout.full_screen_image);
                        fImage = dialog.findViewById(R.id.image_fs);
                        cButton = dialog.findViewById(R.id.close_fs);
                        Glide.with(EntireMyPostActivity.this).asBitmap().fitCenter().load(url4).into(fImage);
                        dialog.show();
                        cButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                image5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView fImage;
                        Button cButton;
                        dialog.setContentView(R.layout.full_screen_image);
                        fImage = dialog.findViewById(R.id.image_fs);
                        cButton = dialog.findViewById(R.id.close_fs);
                        Glide.with(EntireMyPostActivity.this).asBitmap().fitCenter().load(url5).into(fImage);
                        dialog.show();
                        cButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
