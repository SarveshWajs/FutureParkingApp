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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompoundPaymentActivity extends AppCompatActivity {
    private String phoneNumb;
    EditText mSearchPlate;
    Button mSearch;

    private DatabaseReference Reference;
    @Override
    public void onBackPressed() {

        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compound_payment_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phoneNumb = getIntent().getExtras().get("phoneNumber").toString();

        mSearch = findViewById(R.id.search);
        mSearchPlate = findViewById(R.id.searchPlate);

        //Search Number Plate
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plate = mSearchPlate.getText().toString().trim();

                if (TextUtils.isEmpty(plate)) {
                    Toast.makeText(CompoundPaymentActivity.this, "Please enter a number plate", Toast.LENGTH_SHORT).show();
                } else {
                    SearchNumberPlate(plate);
                }
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
                    Intent intent = new Intent(CompoundPaymentActivity.this, CompoundInfoActivity.class);
                    intent.putExtra("plate",plate);
                    intent.putExtra("phoneNumber",phoneNumb);
                    startActivity(intent);
                } else {
                    Toast.makeText(CompoundPaymentActivity.this, "This number plate does not exist in our database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void home_clicked(View view) {
        startActivity(new Intent(CompoundPaymentActivity.this, HomeActivity.class));

    }

}
