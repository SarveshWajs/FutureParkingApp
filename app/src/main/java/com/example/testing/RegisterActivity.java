package com.example.testing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    TextView mLogin;
    EditText mUsername, mPassword,mEmail, mPhoneNo;
    Button mSignUp;
    @Override
    public void onBackPressed() {

        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mEmail = findViewById(R.id.email);
        mPhoneNo = findViewById(R.id.phoneno);
        mSignUp = findViewById(R.id.signup_btn);
        mLogin = findViewById(R.id.login);




        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String phoneno = mPhoneNo.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    mUsername.setError("Username required");
                }
                else if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password required");
                }
                else if (password.length() < 8){
                    mPassword.setError("Password must have at least 8 characters");
                }
                else if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                }
                else if (TextUtils.isEmpty(phoneno)){
                    mPhoneNo.setError("Phone number is required");
                } else {
                    ValidatephoneNumber(username,password,email,phoneno);
                }
            }
        });
    }





    private void ValidatephoneNumber(String username, String password, String email, String phoneno) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(phoneno).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phoneno);
                    userdataMap.put("username", username);
                    userdataMap.put("password", password);
                    userdataMap.put("email", email);

                    RootRef.child("Users").child(phoneno).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Congratulations! Account Created", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Network Issue, try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "This number already created an account!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
