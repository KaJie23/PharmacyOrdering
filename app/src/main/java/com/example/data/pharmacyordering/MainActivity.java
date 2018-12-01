package com.example.data.pharmacyordering;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvCart;
    DatabaseReference dref;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView tvTotal;
    Integer totalAmount = 0;
    Button btnContinuePayment;
    Integer count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCart = findViewById(R.id.lv_cart);
        tvTotal = findViewById(R.id.tv_total);
        btnContinuePayment = findViewById(R.id.btn_continue_payment);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
        lvCart.setAdapter(adapter);
        dref = FirebaseDatabase.getInstance().getReference().child("carts");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = count.toString()+ ".      Medicine Name : " + ds.child("medicineName").getValue(String.class);
                    Integer price = ds.child("medicinePrice").getValue(Integer.class);
                    Integer quantity = ds.child("orderQuantity").getValue(Integer.class);
                    String sprice = "         Price : RM" + price.toString();
                    String squantity = "         Order Quantity : " + quantity.toString();
                    String total = "         Total : RM" + (price*quantity);
                    totalAmount = totalAmount + price*quantity;
                    list.add(name);
                    list.add(sprice);
                    list.add(squantity);
                    list.add(total);
                    adapter.notifyDataSetChanged();
                    count++;
                }
                tvTotal.setText("Total Price: RM" + totalAmount.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnContinuePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaymentMethod.class);
                startActivity(intent);
            }
        });
    }
}
