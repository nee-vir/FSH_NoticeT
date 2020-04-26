package com.example.fsh_noticet;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostsFragment extends Fragment {
    private Button newPost;
    private RecyclerView myPostRecyclerView;
    private MyPostAdapter adapter;
    private DatabaseReference databaseReference;
    private List<DepartmentPost> myPostUploadList;
    private FirebaseAuth fAuth;
    private String uId;


    public MyPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fAuth=FirebaseAuth.getInstance();
        uId=fAuth.getCurrentUser().getUid();
        final NavController navController= Navigation.findNavController(view);
        myPostRecyclerView=view.findViewById(R.id.my_posts_recycler_view);
        myPostRecyclerView.setHasFixedSize(true);
        myPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myPostUploadList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("MY_POSTS").child(uId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myPostUploadList.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    DepartmentPost departmentPost=postSnapshot.getValue(DepartmentPost.class);
                    myPostUploadList.add(departmentPost);
                }
                Collections.reverse(myPostUploadList);
                adapter=new MyPostAdapter(getContext(),myPostUploadList);
                myPostRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}
