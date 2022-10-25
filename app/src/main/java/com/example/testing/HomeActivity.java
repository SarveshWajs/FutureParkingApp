package com.example.testing;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private String phoneNumb;
    EditText mSearchPlate;
    Button mSearch;
    TextView  MyVehicle, myProfile,mVehicle, mProfile,myCoumpound,Username;
    private DatabaseReference Reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phoneNumb = getIntent().getExtras().get("phoneNumber").toString();

        myProfile = findViewById(R.id.viewProfile);
        myCoumpound = findViewById(R.id.viewCoumpound);
        MyVehicle = findViewById(R.id.myVehicles);
        Username = findViewById(R.id.myUsername);
        mVehicle = findViewById(R.id.mVehicles);
        mSearch = findViewById(R.id.search);
        mSearchPlate = findViewById(R.id.searchPlate);
        mProfile = findViewById(R.id.editProfile);

        AccessUserData(phoneNumb);

        //Open My Profile Page
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("phoneNumber",phoneNumb);
                startActivity(intent);
            }
        });

        //Open My Coumpound Page
        myCoumpound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CompoundPaymentActivity.class);
                intent.putExtra("phoneNumber",phoneNumb);
                startActivity(intent);
            }
        });

        //Open Profile Edit  Page
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileUpdateActivity.class);
                intent.putExtra("phoneNumber",phoneNumb);
                startActivity(intent);;
            }
        });
         //Open add Vehicle Page
        MyVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, VehicleActivity.class);
                intent.putExtra("phoneNumber",phoneNumb);
                startActivity(intent);
            }
        });

        //Open Vehicle Selection Page
        mVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewVehicleActivity.class);
                intent.putExtra("phoneNumber",phoneNumb);
                startActivity(intent);
            }
        });

        //Search Number Plate
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plate = mSearchPlate.getText().toString().trim();

                if (TextUtils.isEmpty(plate)){
                    Toast.makeText(HomeActivity.this, "Please enter a number plate", Toast.LENGTH_SHORT).show();
                } else {
                    SearchNumberPlate(plate);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(HomeActivity.this,"Please Logout to go go back to previous  page",Toast.LENGTH_LONG).show();
        return;
    }
    private void AccessUserData(final String phoneNumb) {
        Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(phoneNumb);
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("username").getValue().toString();


                //Set text on XML file
                Username.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SearchNumberPlate(String plate) {
        final DatabaseReference Reference;
        Reference = FirebaseDatabase.getInstance().getReference();

        Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.child("Cars").child(plate).exists())){
                    Intent intent = new Intent(HomeActivity.this,CarInformationActivity.class);
                    intent.putExtra("plate",plate);
                    intent.putExtra("phoneNumber",phoneNumb);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "This number plate does not exist in our database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void logout_clicked(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));

    }

    public void complain_clicked(View view) {
        startActivity(new Intent(HomeActivity.this, ComplainActivity.class));

    }
}


