package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class addvideo extends AppCompatActivity {
String gimg="null",gname="first";
    boolean rec_load_button=true;
    int ok=0;
    VideoView videoView;
    Button browse,upload;
    EditText vtitle;
    Uri videouri;
    MediaController mediaController;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    Button rec_load;
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private Uri videoUri;


    private String videoFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvideo);
        vtitle=(EditText)findViewById(R.id.title_addvideo);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("myvideos");


        videoView=(VideoView)findViewById(R.id.videoView);
        upload=(Button) findViewById(R.id.upload_addvideo);
        //browse=(Button) findViewById(R.id.browse_addvideo);
        mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();
        rec_load = (Button) findViewById(R.id.rec_load);

        rec_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addvideo.this);
                builder.setTitle("Select an option");
                final String[] items = {"From phone", "From camera"};

                final int[] selectedItem = {-1}; // To keep track of the selected item

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the selection here
                        selectedItem[0] = which; // Store the selected item index
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedItem[0] == 0) {
                            Dexter.withContext(getApplicationContext())
                                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    .withListener(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                            Intent intent=new Intent();
                                            ok=5;
                                            upload.setEnabled(true);
                                            intent.setType("video/*");
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            startActivityForResult(intent,101);

                                        }

                                        @Override
                                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                            permissionToken.continuePermissionRequest();
                                        }
                                    }).check();
                        } else if (selectedItem[0] == 1) {
                            // "From camera" selected, open CameraActivity
                            Dexter.withContext(getApplicationContext())
                                    .withPermissions(
                                            Manifest.permission.CAMERA,
                                            Manifest.permission.RECORD_AUDIO,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    )
                                    .withListener(new MultiplePermissionsListener() {
                                        @Override
                                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                                            rec_load_button=true;
                                            ok=5;
                                            upload.setEnabled(true);
                                            // Handle permissions results here
                                            dispatchTakeVideoIntent();
                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                            // Handle permission rationale here and continue with permission request
                                            token.continuePermissionRequest();
                                        }
                                    }).check();
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog on Cancel
                    }
                });

// Set the icon using the resource ID of the image
                builder.setIcon(R.drawable.video);

// Show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(addvideo.this);
//                builder.setTitle("Choose video");
//                builder.setIcon(R.drawable.video);
//                final String[] items = {"From phone", "From camera"};
//
//                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Handle the selection here
//                        if (which == 0) {
//                            // "From phone" selected, open PhoneActivity
//                            Dexter.withContext(getApplicationContext())
//                                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                                    .withListener(new PermissionListener() {
//                                        @Override
//                                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                                            Intent intent=new Intent();
//                                            intent.setType("video/*");
//                                            intent.setAction(Intent.ACTION_GET_CONTENT);
//                                            startActivityForResult(intent,101);
//
//                                        }
//
//                                        @Override
//                                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                                        }
//
//                                        @Override
//                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                                            permissionToken.continuePermissionRequest();
//                                        }
//                                    }).check();
//                        } else if (which == 1) {
//                            // "From camera" selected, open CameraActivity
//                            Dexter.withContext(getApplicationContext())
//                                    .withPermissions(
//                                            Manifest.permission.CAMERA,
//                                            Manifest.permission.RECORD_AUDIO,
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                                    )
//                                    .withListener(new MultiplePermissionsListener() {
//                                        @Override
//                                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                                            rec_load_button=true;
//                                            // Handle permissions results here
//                                            dispatchTakeVideoIntent();
//                                        }
//
//                                        @Override
//                                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                                            // Handle permission rationale here and continue with permission request
//                                            token.continuePermissionRequest();
//                                        }
//                                    }).check();
//                        }
//                        dialog.dismiss(); // Close the dialog
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss(); // Close the dialog on Cancel
//                    }
//                });
//
//// Show the AlertDialog
//                AlertDialog dialog = builder.create();
//                dialog.show();

//                Dexter.withContext(getApplicationContext())
//                        .withPermissions(
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.RECORD_AUDIO,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        )
//                        .withListener(new MultiplePermissionsListener() {
//                            @Override
//                            public void onPermissionsChecked(MultiplePermissionsReport report) {
//                                rec_load_button=true;
//                                // Handle permissions results here
//                                dispatchTakeVideoIntent();
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                                // Handle permission rationale here and continue with permission request
//                                token.continuePermissionRequest();
//                            }
//                        }).check();

            }
        });
//        browse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dexter.withContext(getApplicationContext())
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                                Intent intent=new Intent();
//                                intent.setType("video/*");
//                                intent.setAction(Intent.ACTION_GET_CONTENT);
//                                startActivityForResult(intent,101);
//
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                                permissionToken.continuePermissionRequest();
//                            }
//                        }).check();
//
//
//            }
//        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    processvideopuloading();


            }
        });


    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (rec_load_button&&(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) ){
            videoUri = data.getData();
            videouri=videoUri;
            videoView.setVideoURI( videouri);
            // Show the dialog with options to upload or discard the video
           // showUploadDialog();
            rec_load_button=false;
        }
        if(rec_load_button&&(requestCode==101&&resultCode==RESULT_OK)){


            videouri=data.getData();
            videoView.setVideoURI(videouri);
        }

    }

    public String getExtension(){
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(videouri));
    }
    public void processvideopuloading(){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("media uploader");
        pd.show();




        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String userId2=user.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("userprofile");

        userRef.child(userId2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String uname = dataSnapshot.child("uname").getValue(String.class);
                    String uimage = dataSnapshot.child("uimage").getValue(String.class);
                    gimg=uimage;
                    gname=uname;
                   // Toast.makeText(getApplicationContext(),uname,Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors here
            }
        });








        StorageReference uploader=storageReference.child(("myvideos/"+System.currentTimeMillis()+"."+getExtension()));
        uploader.putFile(videouri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                                final String userId22=user.getUid();

                                filemodel2 obj=new filemodel2(vtitle.getText().toString(),gimg,gname,userId22,uri.toString());
                                databaseReference.child(databaseReference.push().getKey()).setValue(obj)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                               pd.dismiss();
                                                Toast.makeText(getApplicationContext(),"Successfully uploaded",Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                   pd.dismiss();
                                                Toast.makeText(getApplicationContext(),"failed uploaded",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("uploaded :"+(int)per+"%");

                    }
                });

    }
}