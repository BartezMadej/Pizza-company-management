package com.pzm.pizzera.orders_history;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersHistoryInteractor {

	private ArrayList<String> ordersIDs;
	private DatabaseReference dataRef;
	OrdersHistoryInteractor() {
		ordersIDs=new ArrayList<>();
		dataRef=FirebaseDatabase.getInstance().getReference("orders");
	}

	interface OnOrdersHistoryListener {
		void onDatabaseError();
		void onAdapterSet(ArrayList<String> orders);
	}
	void getOrdersIds(final OnOrdersHistoryListener listener)
	{
		dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(!dataSnapshot.exists())
					return;
				for(DataSnapshot data : dataSnapshot.getChildren())
				{
					String key=data.getKey();
					ordersIDs.add(key);
				}
				listener.onAdapterSet(ordersIDs);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}
}
