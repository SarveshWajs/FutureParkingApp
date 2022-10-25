package com.example.testing;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileUpdateActivity extends AppCompatActivity {
    Button submit;
    TextView newUsername,newPassword,newEmail;
    DatabaseReference Reference;

    public void onBackPressed() {

        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String phoneNumb = getIntent().getExtras().get("phoneNumber").toString();

        newUsername = findViewById(R.id.usernameCHG);
        newPassword = findViewById(R.id.passCHG);
        newEmail = findViewById(R.id.emailCHG);
        submit = findViewById(R.id.button2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NewUsername = newUsername.getText().toString();
                String NewPassword = newPassword.getText().toString();
                String NewEmail = newEmail.getText().toString();

                if (TextUtils.isEmpty(NewUsername)){
                    newUsername.setError("New Username required");
                }
                else if (TextUtils.isEmpty(NewPassword)){
                    newPassword.setError("New Password required");
                }
                else if (NewPassword.length() < 8){
                    newPassword.setError("Password must have at least 8 characters");
                }
                else if (TextUtils.isEmpty(NewEmail)){
                    newEmail.setError("Email is required");
                }
                else {
                    Reference = FirebaseDatabase.getInstance().getReference().child("Users");

                    HashMap hashMap = new HashMap();
                    hashMap.put("username",NewUsername);
                    hashMap.put("password",NewPassword);
                    hashMap.put("email",NewEmail);

                    Reference.child(phoneNumb).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(ProfileUpdateActivity.this, "Updated your information successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProfileUpdateActivity.this, ProfileActivity.class);
                            intent.putExtra("phoneNumber",phoneNumb);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    public void home_clicked(View view) {
        startActivity(new Intent(ProfileUpdateActivity.this, HomeActivity.class));

    }
}
