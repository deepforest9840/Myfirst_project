package com.example.myfirst_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Random;

public class secondActivity extends AppCompatActivity {
    Button back,browse,signup;
    ImageView img;
    Bitmap bitmap;
    EditText roll,name,course,contact;
    Uri filepath;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(), "This a toast message", Toast.LENGTH_LONG).show();
        setContentView(R.layout.second);


        back=(Button) findViewById(R.id.back);
        img=(ImageView)findViewById(R.id.img2);
        signup=(Button)findViewById(R.id.signup);
        browse=(Button)findViewById(R.id.browse2);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(secondActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Intent intent=new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(Intent.createChooser(intent,"select image file"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        }).check();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtofirebase();
            }
        });












        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                Intent i = new Intent(secondActivity.this,MainActivity.class);
                startActivity(i);
            } });


    }

    private void uploadtofirebase() {
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("file uploaded from second activity");
        dialog.show();
        name=(EditText) findViewById(R.id.name);
        roll=(EditText) findViewById(R.id.roll);
        course=(EditText) findViewById(R.id.course);
        contact=(EditText) findViewById(R.id.contact);
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(50));
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        dialog.dismiss();
                                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                                        DatabaseReference root=db.getReference("user");
                                        dataholder_second obj=new dataholder_second(name.getText().toString(),contact.getText().toString(),course.getText().toString(),uri.toString());
                                        root.child(roll.getText().toString()).setValue(obj);
                                        name.setText("");
                                        course.setText("");
                                        roll.setText("");
                                        contact.setText("");
                                        img.setImageResource(R.drawable.ic_launcher_background);
                                        Toast.makeText(getApplicationContext(),"uploadd the second task",Toast.LENGTH_LONG).show();




                                    }
                                });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("uploaded :"+(int)percent+"%");


                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {

                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }
        catch(Exception ex){

        }
    }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
