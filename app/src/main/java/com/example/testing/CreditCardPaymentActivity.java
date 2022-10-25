package com.example.testing;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreditCardPaymentActivity extends AppCompatActivity {

    TextView cardid,t2,t3,t4,t5;
    EditText e1,e2,e3,e4,cardholder,months,years,CVV;
    Button proceed,next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditcard_payment_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        e1 = (EditText) findViewById(R.id.et1);
        e2 = (EditText)findViewById(R.id.et2);
        e3 = (EditText)findViewById(R.id.et3);
        e4 = (EditText)findViewById(R.id.et4);
        cardholder = (EditText)findViewById(R.id.et5);
        months =(EditText) findViewById(R.id.et6);
        years = (EditText)findViewById(R.id.et7);
        CVV = (EditText)findViewById(R.id.et8);

        cardid = (TextView) findViewById(R.id.cardid);
        t2 = (TextView)findViewById(R.id.cardholder);
        t3 = (TextView)findViewById(R.id.month);
        t4 = (TextView)findViewById(R.id.year);
        t5 = (TextView)findViewById(R.id.cvv);

        proceed=(Button)findViewById(R.id.btn);
        next=(Button)findViewById(R.id.btn2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreditCardPaymentActivity.this, CreditCardPaymentActivity.class));
                Toast.makeText(CreditCardPaymentActivity.this, "Payment Done Succesfully", Toast.LENGTH_SHORT).show();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference root=db.getReference("Card");

                cardholder obj=new cardholder(cardid.getText().toString(),months.getText().toString(),years.getText().toString(),CVV.getText().toString());
                root.child(cardholder.getText().toString()).setValue(obj);

                String code1,code2,code3,code4;

                code1=e1.getText().toString();
                code2=e2.getText().toString();
                code3=e3.getText().toString();
                code4=e4.getText().toString();

                cardid.setText(code1 + "\t" + code2 + "\t" + code3 + "\t" + code4);

                String name,month,year,cvv;

                name=cardholder.getText().toString();
                t2.setText(name);

                month=months.getText().toString();
                t3.setText(month);

                year=years.getText().toString();
                t4.setText(year);

                cvv=CVV.getText().toString();
                t5.setText(cvv);


            }
        });


    }


}
