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

public class CompoundInfoActivity extends AppCompatActivity {
    private String carPlate;
    DatabaseReference DatabaseRef;
    TextView Plate, Compound, Price;
    String phoneNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compound_info_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String carPlate = getIntent().getExtras().get("plate").toString();

        Plate = findViewById(R.id.carPlate);
        Compound = findViewById(R.id.carCompound);
        Price = findViewById(R.id.carPrice);


        DisplayCarInfo(carPlate);

    }
    private void DisplayCarInfo(final String carPlate) {
        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("Cars").child(carPlate); //carPlate is string from getIntent
        DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String plate = snapshot.child("number").getValue().toString();
                String compound = snapshot.child("compound").getValue().toString();
                String price = snapshot.child("price").getValue().toString();


                // Set text on XML File to display data from database
                Plate.setText(plate);
                Compound.setText(compound);
                Price.setText(price);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void pay_clicked(View view) {
        startActivity(new Intent(CompoundInfoActivity.this, CreditCardPaymentActivity.class));

    }
    public void home_clicked(View view) {
        startActivity(new Intent(CompoundInfoActivity.this, CompoundPaymentActivity.class));

    }
}