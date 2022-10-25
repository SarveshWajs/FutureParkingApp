package com.example.testing;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ComplainActivity extends AppCompatActivity {
    EditText username,phone,mail,complain;
    Uri filepath;
    ImageView img;
    Button browse,signup;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        img=(ImageView)findViewById(R.id.img);
        browse=(Button)findViewById(R.id.browse);
        signup=(Button)findViewById(R.id.upload);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(ComplainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                            }


                        }).check();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtofirebase();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uploadtofirebase()
    {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();

        username=(EditText)findViewById(R.id.t1);
        phone=(EditText)findViewById(R.id.t2);
        mail=(EditText)findViewById(R.id.t3);
        complain=(EditText)findViewById(R.id.t4);


        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(50));

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri){

                                dialog.dismiss();
                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference root=db.getReference("Complain");

                                dataholder obj=new dataholder(phone.getText().toString(),mail.getText().toString(),complain.getText().toString(),uri.toString());
                                root.child(username.getText().toString()).setValue(obj);

                                username.setText("");
                                phone.setText("");
                                mail.setText("");
                                complain.setText("");
                                img.setImageResource(R.drawable.ic_launcher_background);
                                Toast.makeText(getApplicationContext(),"Uploading",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploading :"+(int)percent+" %");
                    }
                });

    }


}
