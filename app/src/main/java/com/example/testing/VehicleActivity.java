package com.example.testing;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class VehicleActivity extends AppCompatActivity {
    Button mAddVehicle;
    EditText carPlate, mCarModel, carDes;
    String number,description,model;
    private DatabaseReference CarRef;
    String phoneNumb;
    @Override
    public void onBackPressed() {

        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_add_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        CarRef = FirebaseDatabase.getInstance().getReference().child("Cars");
        phoneNumb = getIntent().getExtras().get("phoneNumber").toString();

        carPlate = findViewById(R.id.numberPlate);
        mCarModel = findViewById(R.id.carModel);
        carDes = findViewById(R.id.carDescription);
        mAddVehicle = findViewById(R.id.addVehicle);

        mAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCarData();
            }
        });
    }
    private void AddCarData() {
        number = carPlate.getText().toString();
        description = carDes.getText().toString();
        model = mCarModel.getText().toString();

        if (TextUtils.isEmpty(number)){
            Toast.makeText(this,"Please input car number plate",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(model)){
            Toast.makeText(this, "Please input car model", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)){
            Toast.makeText(this, "Please input car description like car colour", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreCarData();
        }
    }
    private void StoreCarData() {
        CarRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child(number).exists())){ //Check if the car number plate exist in the database
                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("number",number);
                    productMap.put("description", description);
                    productMap.put("model",model);
                    productMap.put("phone",phoneNumb);

                    CarRef.child(number).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(VehicleActivity.this, "Car Registered Successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(VehicleActivity.this, ViewVehicleActivity.class);
                                intent.putExtra("phoneNumber",phoneNumb);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(VehicleActivity.this, "This number plate is already registered in our database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void home_clicked(View view) {
        startActivity(new Intent(VehicleActivity.this, HomeActivity.class));

    }

}

