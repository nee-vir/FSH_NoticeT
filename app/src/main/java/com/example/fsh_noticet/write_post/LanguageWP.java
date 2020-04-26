package com.example.fsh_noticet.write_post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fsh_noticet.DepartmentPost;
import com.example.fsh_noticet.R;
import com.example.fsh_noticet.TeacherInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class LanguageWP extends AppCompatActivity {
    private Spinner spinnerCourse,spinnerYear;
    private Button csPostButton;
    private EditText csTitle, csBody;
    private ProgressBar csProgressBar;
    private ImageView image1, image2, image3, image4, image5;
    private Uri imageUri1;
    private Uri imageUri2;
    private Uri imageUri3;
    private Uri imageUri4;
    private Uri imageUri5;
    private StorageTask uploadTask;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private DocumentReference documentReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;
    private final int IMAGE_REQUEST1 = 5551;
    private final int IMAGE_REQUEST2 = 5552;
    private final int IMAGE_REQUEST3 = 5553;
    private final int IMAGE_REQUEST4 = 5554;
    private final int IMAGE_REQUEST5 = 5555;
    private Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_wp);
        spinnerYear = findViewById(R.id.lg_choose_year);
        spinnerCourse=findViewById(R.id.lg_courses);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this, R.array.Lang_Courses, android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(courseAdapter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choose_year_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter);
        String text = spinnerYear.getSelectedItem().toString();
        csTitle = findViewById(R.id.lg_title);
        csProgressBar = findViewById(R.id.lg_progress_bar);
        csProgressBar.setVisibility(View.INVISIBLE);
        csBody = findViewById(R.id.lg_post_body);
        image1 = findViewById(R.id.image_1);
        image2 = findViewById(R.id.image_2);
        image3 = findViewById(R.id.image_3);
        image4 = findViewById(R.id.image_4);
        image5 = findViewById(R.id.image_5);
        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);
        image4.setVisibility(View.INVISIBLE);
        image5.setVisibility(View.INVISIBLE);
        csPostButton = findViewById(R.id.lg_post_button);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("LANGUAGE_POSTS");
        storageReference = FirebaseStorage.getInstance().getReference("LANGUAGE_IMAGES");
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST1);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST2);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST3);
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST4);
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST5);
            }
        });
        image1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageUri1=null;
                imageUri2=null;
                imageUri3=null;
                imageUri4=null;
                imageUri5=null;
                image1.setImageDrawable(null);
                image2.setImageDrawable(null);
                image3.setImageDrawable(null);
                image4.setImageDrawable(null);
                image5.setImageDrawable(null);
                image2.setVisibility(View.INVISIBLE);
                image3.setVisibility(View.INVISIBLE);
                image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        image2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageUri2=null;
                imageUri3=null;
                imageUri4=null;
                imageUri5=null;
                image2.setImageDrawable(null);
                image3.setImageDrawable(null);
                image4.setImageDrawable(null);
                image5.setImageDrawable(null);
                image3.setVisibility(View.INVISIBLE);
                image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        image3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageUri3=null;
                imageUri4=null;
                imageUri5=null;
                image3.setImageDrawable(null);
                image4.setImageDrawable(null);
                image5.setImageDrawable(null);
                image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        image4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageUri4=null;
                imageUri5=null;
                image4.setImageDrawable(null);
                image5.setImageDrawable(null);
                image5.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        image5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageUri5=null;
                image5.setImageDrawable(null);
                return true;
            }
        });

        csPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask!=null&&uploadTask.isInProgress()){
                    Toast.makeText(LanguageWP.this, "Upload In Progress", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(csTitle.getText().toString().isEmpty()||csBody.getText().toString().isEmpty()){
                        Toast.makeText(LanguageWP.this,"Title and body fields can't be left empty",Toast.LENGTH_LONG).show();
                    }
                    else {
                        postUpload();
                    }

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri1 = data.getData();
            Glide.with(this).asBitmap().load(imageUri1).centerCrop().into(image1);
            image2.setVisibility(View.VISIBLE);
        }
        if (requestCode == IMAGE_REQUEST2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri2 = data.getData();
            Glide.with(this).asBitmap().load(imageUri2).centerCrop().into(image2);
            image3.setVisibility(View.VISIBLE);
        }
        if (requestCode == IMAGE_REQUEST3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri3 = data.getData();
            Glide.with(this).asBitmap().load(imageUri3).centerCrop().into(image3);
            image4.setVisibility(View.VISIBLE);
        }
        if (requestCode == IMAGE_REQUEST4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri4 = data.getData();
            Glide.with(this).asBitmap().load(imageUri4).centerCrop().into(image4);
            image5.setVisibility(View.VISIBLE);
        }
        if (requestCode == IMAGE_REQUEST5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri5 = data.getData();
            Glide.with(this).asBitmap().load(imageUri5).centerCrop().into(image5);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void postUpload() {
        csProgressBar.setProgress(0);
        csProgressBar.setVisibility(View.VISIBLE);
        String postId = databaseReference.push().getKey();
        String imageUrl1, imageUrl2, imageUrl3, imageUrl4, imageUrl5;
        uploadImages(postId, imageUri1, imageUri2, imageUri3, imageUri4, imageUri5);


    }

    private void uploadImages(String id, Uri uri1, final Uri uri2, final Uri uri3, final Uri uri4, final Uri uri5) {
        String imageUrl;
        final String pID = id;
        if (uri1 != null) {
            final StorageReference fileRef = storageReference.child(id).child(System.currentTimeMillis() + "." + getFileExtension(uri1));
            uploadTask = fileRef.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri url1 = uri;
                            if (uri2 != null) {
                                final StorageReference fileRef2 = storageReference.child(pID).child(System.currentTimeMillis() + "." + getFileExtension(uri2));
                                uploadTask = fileRef2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        fileRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                final Uri url2 = uri;
                                                if (uri3 != null) {
                                                    final StorageReference fileRef3 = storageReference.child(pID).child(System.currentTimeMillis() + "." + getFileExtension(uri3));
                                                    uploadTask=fileRef3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            fileRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    final Uri url3 = uri;
                                                                    if (uri4 != null) {
                                                                        final StorageReference fileRef4 = storageReference.child(pID).child(System.currentTimeMillis() + "." + getFileExtension(uri4));
                                                                        uploadTask=fileRef4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                fileRef4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                    @Override
                                                                                    public void onSuccess(Uri uri) {
                                                                                        final Uri url4=uri;
                                                                                        if(uri5!=null){
                                                                                            final StorageReference fileRef5 = storageReference.child(pID).child(System.currentTimeMillis() + "." + getFileExtension(uri5));
                                                                                            uploadTask=fileRef5.putFile(uri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                @Override
                                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                    fileRef5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Uri uri) {
                                                                                                            final Uri url5=uri;
                                                                                                            final String course=spinnerCourse.getSelectedItem().toString();
                                                                                                            final String year=spinnerYear.getSelectedItem().toString();
                                                                                                            final String title=csTitle.getText().toString();
                                                                                                            final String body=csBody.getText().toString();
                                                                                                            final String dName="Computer Applications";
                                                                                                            String uId=firebaseAuth.getCurrentUser().getUid();
                                                                                                            documentReference=fStore.collection("Teachers").document(uId);
                                                                                                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                    TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                                                                                                                    String userName=teacherInfo.getFullName();
                                                                                                                    currentDate= Calendar.getInstance().getTime();
                                                                                                                    String dateTime= DateFormat.getDateTimeInstance().format(currentDate);
                                                                                                                    DepartmentPost departmentPost=new DepartmentPost(title,body,dName,course,year,url1.toString(),
                                                                                                                            url2.toString(),url3.toString(),url4.toString(),url5.toString(),userName,dateTime,"LG",pID);
                                                                                                                    databaseReference.child(pID).setValue(departmentPost);
                                                                                                                    String userId=firebaseAuth.getCurrentUser().getUid();
                                                                                                                    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("MY_POSTS");
                                                                                                                    databaseReference2.child(userId).child(pID).setValue(departmentPost);
                                                                                                                    Toast.makeText(LanguageWP.this, "Posted", Toast.LENGTH_SHORT).show();
                                                                                                                    finish();
                                                                                                                }
                                                                                                            });

                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    Toast.makeText(LanguageWP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                                                                @Override
                                                                                                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                                                                                    csProgressBar.setProgress((int)progress);
                                                                                                }
                                                                                            });


                                                                                        }
                                                                                        else {
                                                                                            final String course=spinnerCourse.getSelectedItem().toString();
                                                                                            final String year=spinnerYear.getSelectedItem().toString();
                                                                                            final String title=csTitle.getText().toString();
                                                                                            final String body=csBody.getText().toString();
                                                                                            final String dName="Computer Applications";
                                                                                            String uId=firebaseAuth.getCurrentUser().getUid();
                                                                                            documentReference=fStore.collection("Teachers").document(uId);
                                                                                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                @Override
                                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                    TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                                                                                                    String userName=teacherInfo.getFullName();
                                                                                                    String url5="";
                                                                                                    currentDate= Calendar.getInstance().getTime();
                                                                                                    String dateTime=DateFormat.getDateTimeInstance().format(currentDate);
                                                                                                    DepartmentPost departmentPost=new DepartmentPost(title,body,dName,course,year,url1.toString(),
                                                                                                            url2.toString(),url3.toString(),url4.toString(),url5,userName,dateTime,"LG",pID);
                                                                                                    databaseReference.child(pID).setValue(departmentPost);
                                                                                                    String userId=firebaseAuth.getCurrentUser().getUid();
                                                                                                    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("MY_POSTS");
                                                                                                    databaseReference2.child(userId).child(pID).setValue(departmentPost);
                                                                                                    Toast.makeText(LanguageWP.this, "Posted", Toast.LENGTH_SHORT).show();
                                                                                                    finish();
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(LanguageWP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                                                                double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                                                                csProgressBar.setProgress((int)progress);
                                                                            }
                                                                        });

                                                                    }
                                                                    else{
                                                                        final String course=spinnerCourse.getSelectedItem().toString();
                                                                        final String year=spinnerYear.getSelectedItem().toString();
                                                                        final String title=csTitle.getText().toString();
                                                                        final String body=csBody.getText().toString();
                                                                        final String dName="Computer Applications";
                                                                        String uId=firebaseAuth.getCurrentUser().getUid();
                                                                        documentReference=fStore.collection("Teachers").document(uId);
                                                                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                                                                                String userName=teacherInfo.getFullName();
                                                                                String url4="";
                                                                                String url5="";
                                                                                currentDate= Calendar.getInstance().getTime();
                                                                                String dateTime=DateFormat.getDateTimeInstance().format(currentDate);
                                                                                DepartmentPost departmentPost=new DepartmentPost(title,body,dName,course,year,url1.toString(),
                                                                                        url2.toString(),url3.toString(),url4,url5,userName,dateTime,"LG",pID);
                                                                                databaseReference.child(pID).setValue(departmentPost);
                                                                                String userId=firebaseAuth.getCurrentUser().getUid();
                                                                                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("MY_POSTS");
                                                                                databaseReference2.child(userId).child(pID).setValue(departmentPost);
                                                                                Toast.makeText(LanguageWP.this, "Posted", Toast.LENGTH_SHORT).show();
                                                                                finish();
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(LanguageWP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                                            csProgressBar.setProgress((int)progress);
                                                        }
                                                    });


                                                }
                                                else{
                                                    final String course=spinnerCourse.getSelectedItem().toString();
                                                    final String year=spinnerYear.getSelectedItem().toString();
                                                    final String title=csTitle.getText().toString();
                                                    final String body=csBody.getText().toString();
                                                    final String dName="Computer Applications";
                                                    String uId=firebaseAuth.getCurrentUser().getUid();
                                                    documentReference=fStore.collection("Teachers").document(uId);
                                                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                                                            String userName=teacherInfo.getFullName();
                                                            String url3="";
                                                            String url4="";
                                                            String url5="";
                                                            currentDate= Calendar.getInstance().getTime();
                                                            String dateTime=DateFormat.getDateTimeInstance().format(currentDate);
                                                            DepartmentPost departmentPost=new DepartmentPost(title,body,dName,course,year,url1.toString(),
                                                                    url2.toString(),url3,url4,url5,userName,dateTime,"LG",pID);
                                                            databaseReference.child(pID).setValue(departmentPost);
                                                            String userId=firebaseAuth.getCurrentUser().getUid();
                                                            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("MY_POSTS");
                                                            databaseReference2.child(userId).child(pID).setValue(departmentPost);
                                                            Toast.makeText(LanguageWP.this, "Posted", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LanguageWP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                        csProgressBar.setProgress((int)progress);
                                    }
                                });


                            }
                            else{
                                final String course=spinnerCourse.getSelectedItem().toString();
                                final String year=spinnerYear.getSelectedItem().toString();
                                final String title=csTitle.getText().toString();
                                final String body=csBody.getText().toString();
                                final String dName="Computer Applications";
                                String uId=firebaseAuth.getCurrentUser().getUid();
                                documentReference=fStore.collection("Teachers").document(uId);
                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                                        String userName=teacherInfo.getFullName();
                                        String url2="";
                                        String url3="";
                                        String url4="";
                                        String url5="";
                                        currentDate= Calendar.getInstance().getTime();
                                        String dateTime=DateFormat.getDateTimeInstance().format(currentDate);
                                        DepartmentPost departmentPost=new DepartmentPost(title,body,dName,course,year,url1.toString(),
                                                url2,url3,url4,url5,userName,dateTime,"LG",pID);
                                        databaseReference.child(pID).setValue(departmentPost);
                                        String userId=firebaseAuth.getCurrentUser().getUid();
                                        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("MY_POSTS");
                                        databaseReference2.child(userId).child(pID).setValue(departmentPost);
                                        Toast.makeText(LanguageWP.this, "Posted", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LanguageWP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    csProgressBar.setProgress((int)progress);
                }
            });

        }
        else{
            final String course=spinnerCourse.getSelectedItem().toString();
            final String year=spinnerYear.getSelectedItem().toString();
            final String title=csTitle.getText().toString();
            final String body=csBody.getText().toString();
            final String dName="Computer Applications";
            String uId=firebaseAuth.getCurrentUser().getUid();
            documentReference=fStore.collection("Teachers").document(uId);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    TeacherInfo teacherInfo=documentSnapshot.toObject(TeacherInfo.class);
                    String userName=teacherInfo.getFullName();
                    String url1="";
                    String url2="";
                    String url3="";
                    String url4="";
                    String url5="";
                    currentDate= Calendar.getInstance().getTime();
                    String dateTime=DateFormat.getDateTimeInstance().format(currentDate);
                    DepartmentPost departmentPost=new DepartmentPost(title,body,dName,course,year,url1,
                            url2,url3,url4,url5,userName,dateTime,"LG",pID);
                    databaseReference.child(pID).setValue(departmentPost);
                    String userId=firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("MY_POSTS");
                    databaseReference2.child(userId).child(pID).setValue(departmentPost);
                    Toast.makeText(LanguageWP.this, "Posted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

    }
}
