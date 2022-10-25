package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    TextView mSignUp;
    EditText editPhone , editTextPassword;
    Button buttonSubmit;
    FirebaseAuth mAuth;



    private String parentDbName = "Users";
    @Override
    public void onBackPressed() {

        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);





        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editPhone = findViewById(R.id.phone);
        editTextPassword = findViewById(R.id.et_password);
        buttonSubmit = findViewById(R.id.bt_submit);
        mAuth=FirebaseAuth.getInstance();
        mSignUp = findViewById(R.id.signUp);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void LoginUser() {
        String phone = editPhone.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please key in your phone number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please key in your password", Toast.LENGTH_SHORT).show();
        } else {
            AllowAccessToAccount (phone, password);
        }
    }
    private void AllowAccessToAccount(final String phone, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()){
                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("phoneNumber",phone);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "This account does not exist, please create one.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}