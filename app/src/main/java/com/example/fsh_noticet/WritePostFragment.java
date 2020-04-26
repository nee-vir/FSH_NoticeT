package com.example.fsh_noticet;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class WritePostFragment extends Fragment {
    private static final String TAG = "WritePostFragment";
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    private EditText title;
    private EditText department;
    private EditText body;
    private Button postButton;
    private ProgressBar uProgress;
    private FirebaseAuth fAuth;
    DocumentReference documentReference;
    StorageReference storageReference;
    StorageReference fileReference;
    private StorageTask pUploadTask;
    private Button addImage;
    private ImageView imageView;
    FirebaseFirestore fStore;
    private String uId;
    private Uri postImageUri;
    private final int PICK_IMAGE_REQUEST=5555;


    public WritePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference2=FirebaseDatabase.getInstance().getReference("OWN_POSTS");
        storageReference= FirebaseStorage.getInstance().getReference("Post_Images");
        fStore=FirebaseFirestore.getInstance();
        title=view.findViewById(R.id.title_post);
        department=view.findViewById(R.id.post_dept_post);
        body=view.findViewById(R.id.post_body);
        postButton=view.findViewById(R.id.post_button);
        uProgress=view.findViewById(R.id.upload_progress_bar);
        uProgress.setVisibility(view.INVISIBLE);
        fAuth=FirebaseAuth.getInstance();
        uId=fAuth.getCurrentUser().getUid();
        imageView=view.findViewById(R.id.image_view1);
        addImage=view.findViewById(R.id.addImage);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: addImage");
                openFileChooser();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pUploadTask!=null&&pUploadTask.isInProgress()){
                    Toast.makeText(getContext(), "Upload In Progress...", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadPost();
                }

            }
        });


    }
    private void openFileChooser(){
        Log.d(TAG, "openFileChooser: Started");
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
        Log.d(TAG, "openFileChooser: Ended");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: Started");
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            postImageUri=data.getData();
            Glide.with(getActivity()).asBitmap().load(postImageUri).into(imageView);
            Log.d(TAG, "onActivityResult: Working");
        }
    }
    private String getFileExtension(Uri uri){
        Log.d(TAG, "getFileExtension: Started");
        ContentResolver contentResolver=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        Log.d(TAG, "getFileExtension: Ended");
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadPost(){
        if(postImageUri!=null) {
            uProgress.setVisibility(View.VISIBLE);
            fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(postImageUri));
            pUploadTask = fileReference.putFile(postImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//To get the download url
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl=uri;
                            final String postT = title.getText().toString().trim();
                            final String postD = department.getText().toString().trim();
                            //String pUsername = userName.getText().toString().trim();
                            final String pBody = body.getText().toString().trim();
                            final String pImageUrl = downloadUrl.toString();
                            DocumentReference docRef=fStore.collection("Teachers").document(uId);
                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                                    String pUsername=teacherInfo.getFullName();
                                    String uploadId = databaseReference.push().getKey();
                                    Upload upload = new Upload(postT, postD, pUsername, pBody, pImageUrl,uploadId);
                                    databaseReference.child(uploadId).setValue(upload);
                                    databaseReference2.child(uId).child(uploadId).setValue(upload);
                                    Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();
                                }
                            });
                            /*Upload upload = new Upload(postT, postD, pUsername, pBody, pImageUrl);
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                            databaseReference2.child(uId).child(uploadId).setValue(upload);
                            Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();*/
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    uProgress.setProgress((int)progress);
                }
            });

        }
        else {
            final String postT=title.getText().toString().trim();
            final String postD=department.getText().toString().trim();
            //String pUsername=userName.getText().toString().trim();
            final String pBody=body.getText().toString().trim();
            final String pImageUrl="";
            DocumentReference documentReference=fStore.collection("Teachers").document(uId);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                    String pUsername=teacherInfo.getFullName();
                    String uploadId = databaseReference.push().getKey();
                    Upload upload = new Upload(postT, postD, pUsername, pBody, pImageUrl,uploadId);
                    databaseReference.child(uploadId).setValue(upload);
                    databaseReference2.child(uId).child(uploadId).setValue(upload);
                    Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            });

        }
    }
}
