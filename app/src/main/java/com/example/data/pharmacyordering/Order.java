package com.example.data.pharmacyordering;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Order extends AppCompatActivity {
    ListView lvOrder;
    DatabaseReference dref;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Integer count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        lvOrder = findViewById(R.id.lv_order);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
        lvOrder.setAdapter(adapter);
        dref = FirebaseDatabase.getInstance().getReference().child("orders");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String orderId = count.toString() + ".    Order ID : " + ds.child("orderID").getValue(String.class);
                    String orderDate = "       Date : " + ds.child("orderDate").getValue(String.class);
                    Integer orderTotalPrice = ds.child("orderTotalPrice").getValue(Integer.class);
                    String sTotalPrice = "       Total Price : RM" + orderTotalPrice.toString();
                    list.add(orderId);
                    list.add(orderDate);
                    list.add(sTotalPrice);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Order.this, OrderDetails.class);
                startActivity(intent);
            }
        });
    }
}
