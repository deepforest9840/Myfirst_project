package com.example.myfirst_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class update_profile extends AppCompatActivity {
    ImageView uimage;
    EditText uname;
    FloatingActionButton floating;
    Button btnupdate;
    ImageView delete;
    Uri filepath;
    Bitmap bitmap;
    String nam="f";
    String Userid="";
    DatabaseReference dbreference;
    StorageReference storageReference;
 @SuppressLint("MissingInflatedId")
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        uimage=(ImageView)findViewById(R.id.uimage);
        uname=(EditText) findViewById(R.id.uname);
        btnupdate=(Button) findViewById(R.id.btnupdate);
        delete=(ImageView)findViewById(R.id.deletecom4);
     floating = (FloatingActionButton) findViewById(R.id.floatinggg);
     if(nam!="f"){if(
             uname.getText().toString()!=nam){
         btnupdate.setEnabled(true);
     }}
     FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
     Userid=user.getUid();
     dbreference= FirebaseDatabase.getInstance().getReference().child("userprofile");
     storageReference= FirebaseStorage.getInstance().getReference();

     floating.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             // Open the camera to capture an image
             Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                 btnupdate.setEnabled(true);
                 startActivityForResult(takePictureIntent, 102); // 101 is a request code
             }
         }
     });

     delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             // Create an AlertDialog
             AlertDialog.Builder builder = new AlertDialog.Builder(update_profile.this);
             builder.setTitle("Confirm Deletion");
             builder.setMessage("Are you sure you want to delete this account?");

             builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     // Delete Firebase user account
                     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                     if (user != null) {
                         user.delete()
                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful()) {
                                             // User account deleted, now delete from the userprofile
                                             DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("userprofile").child(Userid);
                                             Task<Void> mTask = dbr.removeValue();
                                             mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                                                 @Override
                                                 public void onSuccess(Void unused) {
                                                     Toast.makeText(getApplicationContext(), "Account is deleted successfully", Toast.LENGTH_SHORT).show();
                                                     FirebaseAuth.getInstance().signOut();
                                                     startActivity(new Intent(update_profile.this, Login.class));
                                                     finish();
                                                 }
                                             });
                                         } else {
                                             Toast.makeText(getApplicationContext(), "Failed to delete account", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 });
                     }
                 }
             });

             builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Toast.makeText(getApplicationContext(), "Account is not deleted", Toast.LENGTH_SHORT).show();
                 }
             });

             // Show the AlertDialog
             AlertDialog dialog = builder.create();
             dialog.show();
         }
     });


     if(nam!="f"){if(
             uname.getText().toString()!=nam){
         btnupdate.setEnabled(true);
     }}













     uimage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Dexter.withContext(getApplicationContext())
                     .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)

                     .withListener(new PermissionListener() {
                         @Override
                         public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent();
                                intent.setType("image/*");
                             btnupdate.setEnabled(true);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"please select file for update ur profile"),101);

                         }

                         @Override
                         public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                         }

                         @Override
                         public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                             permissionToken.continuePermissionRequest();
                         }
                     }).check();
         }
     });






     btnupdate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             updatetofirebase();
         }
     });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK){
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                uimage.setImageBitmap(bitmap);
            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
       else if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            uimage.setImageBitmap(bitmap); // Display the captured image
            // Convert the Bitmap to a File
            File imageFile = convertBitmapToFile(bitmap);
            filepath = Uri.fromFile(imageFile);

        }
    }


    private File convertBitmapToFile(Bitmap bitmap) {
        File filesDir = getFilesDir();
        File imageFile = new File(filesDir, "captured_image.jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatetofirebase() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File uploader from progress dialog");
        pd.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Userid = user.getUid();
        dbreference = FirebaseDatabase.getInstance().getReference().child("userprofile");

        if (filepath != null) {
            final StorageReference uploader = storageReference.child("profileimages/" + "img" + System.currentTimeMillis());
            uploader.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Map<String, Object> map = new HashMap<>();
                                    map.put("uimage", uri.toString());
                                    map.put("uname", uname.getText().toString());
                                    dbreference.child(Userid).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                dbreference.child(Userid).updateChildren(map);
                                            } else {
                                                dbreference.child(Userid).setValue(map);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(getApplicationContext(), "Cannot be updated or set", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            pd.setMessage("Uploaded: " + (int) percent + "%");
                        }
                    });
        } else {
            // Handle the case where only the name is being updated
            final Map<String, Object> map = new HashMap<>();
            map.put("uname", uname.getText().toString());
            if(uname.getText().toString()!=""){
                btnupdate.setEnabled(true);
            }
            dbreference.child(Userid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        dbreference.child(Userid).updateChildren(map);
                    } else {
                        dbreference.child(Userid).setValue(map);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Cannot be updated or set", Toast.LENGTH_SHORT).show();
                }
            });
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Name updated successfully", Toast.LENGTH_SHORT).show();
        }
    }


    protected  void onStart(){

     super.onStart();
     FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
     Userid=user.getUid();
     dbreference.child(Userid).addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(snapshot.exists()){
                 uname.setText(snapshot.child("uname").getValue().toString());
                 nam=snapshot.child("uname").getValue().toString();
                 if(uname.getText().toString()==nam){
                     btnupdate.setEnabled(false);
                 }
                 if(nam!="f"){
                     if(uname.getText().toString()!=nam){
                     btnupdate.setEnabled(true);
                 }}



                 Glide.with(getApplicationContext()).load(snapshot.child("uimage").getValue().toString()).into(uimage);
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });


    }








}