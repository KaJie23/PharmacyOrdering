package com.example.data.pharmacyordering;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PaymentMethod extends AppCompatActivity {
    RadioGroup rbPaymentMethod;
    RadioButton rbCash, rbCredit, rbDebit;
    Button btnMakePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        final RadioGroup rbPaymentMethod = (RadioGroup) findViewById(R.id.rg_payment_method);
        rbCash = findViewById(R.id.rb_cash);
        rbCredit = findViewById(R.id.rb_credit);
        rbDebit = findViewById(R.id.rb_debit);
        btnMakePayment = findViewById(R.id.btn_make_payment);

        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbCash.isChecked() || rbCredit.isChecked() || rbDebit.isChecked()){
                    Intent intent = new Intent(PaymentMethod.this, Order.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PaymentMethod.this,"Please select a payment method to proceed.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
