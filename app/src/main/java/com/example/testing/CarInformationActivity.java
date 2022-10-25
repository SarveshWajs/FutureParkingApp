package com.example.testing;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarInformationActivity  extends AppCompatActivity {
    private String carPlate;
    DatabaseReference DatabaseRef;
    TextView Plate, Description, Model, Phone;
    String phoneNumb;
    public void onBackPressed() {

        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_info_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String carPlate = getIntent().getExtras().get("plate").toString();

        Plate = findViewById(R.id.carPlate);
        Description = findViewById(R.id.carDescrip);
        Model = findViewById(R.id.carModel2);
        Phone = findViewById(R.id.phoneNum);

        DisplayCarInfo(carPlate);

    }
    private void DisplayCarInfo(final String carPlate) {
        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("Cars").child(carPlate); //carPlate is string from getIntent
        DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String plate = snapshot.child("number").getValue().toString();
                String model = snapshot.child("model").getValue().toString();
                String description = snapshot.child("description").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();

                // Set text on XML File to display data from database
                Plate.setText(plate);
                Model.setText(model);
                Description.setText(description);
                Phone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void bell_clicked(View view) {
        startActivity(new Intent(CarInformationActivity.this, NotificationActivity.class));

    }

}

