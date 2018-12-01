package com.example.data.pharmacyordering;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    ListView lvOrderDetails, lvItemDetails;
    DatabaseReference dref, sdref;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> slist = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> sadapter;
    TextView tvTotal;
    Integer totalAmount = 0;
    Integer count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        lvOrderDetails = findViewById(R.id.lv_order_details);
        lvItemDetails = findViewById(R.id.lv_item_details);
        tvTotal = findViewById(R.id.tv_total);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
        lvOrderDetails.setAdapter(adapter);
        dref = FirebaseDatabase.getInstance().getReference().child("orders");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String orderId = "Order ID : " + ds.child("orderID").getValue(String.class);
                    String orderCustomerName = "Customer Name : " + ds.child("orderCustomerName").getValue(String.class);
                    String orderCustomerPhone = "Customer Phone No. : " + ds.child("orderCustomerPhone").getValue(String.class);
                    String orderCustomerAddress = "Customer Address : " + ds.child("orderCustomerAddress").getValue(String.class);
                    list.add(orderId);
                    list.add(orderCustomerName);
                    list.add(orderCustomerPhone);
                    list.add(orderCustomerAddress);
                    adapter.notifyDataSetChanged();
                    break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,slist);
        lvItemDetails.setAdapter(sadapter);
        sdref = FirebaseDatabase.getInstance().getReference().child("carts");
        sdref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = count.toString() + ".     " + ds.child("medicineName").getValue(String.class);
                    Integer price = ds.child("medicinePrice").getValue(Integer.class);
                    Integer quantity = ds.child("orderQuantity").getValue(Integer.class);
                    String orderQuantity = "         Quantity : " + quantity.toString() + " (RM" + price.toString() + ")";
                    String total = "         RM" + (price*quantity);
                    totalAmount = totalAmount + price*quantity;
                    slist.add(name);
                    slist.add(orderQuantity);
                    slist.add(total);
                    sadapter.notifyDataSetChanged();
                    count++;
                }
                tvTotal.setText("Total Price : RM" + totalAmount.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
