package com.pzm.pizzera.orders_history.order_display;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.wholesale.Order;

import java.util.ArrayList;

public class OrderDisplayInteractor {
	private DatabaseReference dataRef;
	private ArrayList<Order> orderItems;

	interface OnOrderDisplayListener {
		void onDatabaseError();

		void onAdapterSet(ArrayList<Order> orderItems);
	}

	OrderDisplayInteractor() {
		dataRef = FirebaseDatabase.getInstance().getReference("orders");
		orderItems= new ArrayList<>();
	}

	void getOrderItems(final OnOrderDisplayListener listener, String orderId) {
		dataRef.child(orderId).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (!dataSnapshot.exists())
					return;
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					Order order = data.getValue(Order.class);
					orderItems.add(order);
				}
				listener.onAdapterSet(orderItems);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}
}