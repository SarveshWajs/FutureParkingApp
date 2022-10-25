package com.example.testing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.lang.ref.Reference;
import java.util.UUID;

public class  ProfileActivity extends AppCompatActivity {
    TextView Number,Email,Username;
    private DatabaseReference Reference;
    private ImageView profilePic;
    private Uri imageUri;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;

    @Override
    public void onBackPressed() {

        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String phoneNumb = getIntent().getExtras().get("phoneNumber").toString();

        Number = findViewById(R.id.myPhoneNumber);
        Email = findViewById(R.id.myEmail);
        Username = findViewById(R.id.myUsername);
        profilePic=findViewById(R.id.profilePic);
        mStorage=FirebaseStorage.getInstance();
        mStorageReference=mStorage.getReference();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }

        });

        AccessUserData(phoneNumb);
    }
    private void choosePicture() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }
    private void uploadPicture() {

        final ProgressDialog pd =new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = mStorageReference.child("images/" + randomKey);



        mountainsRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded.",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Progress:" +(int) progressPercent + "%");
                    }
                });
    }
        private void AccessUserData(final String phoneNumb) {
            Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(phoneNumb);
            Reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String username = snapshot.child("username").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String number = snapshot.child("phone").getValue().toString();

                    //Set text on XML file
                    Username.setText(username);
                    Email.setText(email);
                    Number.setText(number);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    public void home_clicked(View view) {
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));

    }

    public void profile_clicked(View view) {
        startActivity(new Intent(ProfileActivity.this, ProfileUpdateActivity.class));

    }

}
