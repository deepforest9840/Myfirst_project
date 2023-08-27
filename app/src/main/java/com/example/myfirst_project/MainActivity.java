package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
//
//public class MainActivity extends AppCompatActivity {
//
//    ImageView img;
//    Button browse,upload,button3;
//    Uri filepath;
//    Bitmap bitmap;
//
////    public void secondactivity(View view){
////        Intent i = new Intent(MainActivity.this,secondActivity.class);
////        startActivity(i);
////
////    }
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        img=(ImageView) findViewById(R.id.imageView);
//        upload=(Button) findViewById(R.id.upload);
//        browse=(Button)findViewById(R.id.browse);
//        button3=(Button)findViewById(R.id.button3);
//
//        browse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dexter.withActivity(MainActivity.this)
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse response)  {
//
//                                Intent intent=new Intent(Intent.ACTION_PICK);
//                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent,"pleaser select image"),1);
//
//
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse response) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//                                        token.continuePermissionRequest();
//                            }
//                        }).check();
//            }
//        });
//        upload.setOnClickListener(new View.OnClickListener(){
//
//                public void onClick(View view){
//                    uploadtofirebase();
//                }
//        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//
//                Intent i = new Intent(MainActivity.this,registration_activity.class);
//                startActivity(i);
//            } });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if(requestCode==1&&resultCode==RESULT_OK){
//
//            filepath=data.getData();
//            try{
//                    InputStream inputStream=getContentResolver().openInputStream(filepath);
//                    bitmap= BitmapFactory.decodeStream(inputStream);
//                    img.setImageBitmap(bitmap);
//            }catch(Exception ex){
//
//            }
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//    private void uploadtofirebase(){
//        ProgressDialog dialog=new ProgressDialog(this);
//        dialog.setTitle("File Uploaderrrrrrrrrrrr");
//        dialog.show();
//        FirebaseStorage storage=FirebaseStorage.getInstance();
//        StorageReference uploader=storage.getReference().child("image1");
//        uploader.putFile(filepath)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            dialog.dismiss();
//                            Toast.makeText(getApplicationContext(),"File Uploaded kahatam",Toast.LENGTH_LONG).show();
//
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot tasknSnapshot) {
//                        float percent=(100*tasknSnapshot.getBytesTransferred())/tasknSnapshot.getTotalByteCount();
//                        dialog.setMessage("uploaded is :"+(int)percent+"%");
//
//                    }
//                });
//    }
//}