package com.pzm.pizzera.wholesale;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.List;

public class WholesaleInteractor {


	interface OnWholesaleErrorListener {

		void onAmountError();

		void onOrderPersistError();

		void onSuccess();
	}

	final String TAG = "WholesaleInteractor";
	private final ArrayAdapter<String> vendorsAdapter;
	private final ArrayAdapter<String> productsAdapter;

	DatabaseReference databaseReference;

	public WholesaleInteractor(ArrayAdapter<String> vendorsAdapter, ArrayAdapter<String> productsAdapter) {
		this.vendorsAdapter = vendorsAdapter;
		this.productsAdapter = productsAdapter;
		databaseReference = FirebaseDatabase.getInstance().getReference();
		fetchAdapterItems("products", productsAdapter);
		fetchAdapterItems("vendors", vendorsAdapter);
	}

	void fetchAdapterItems(String child, ArrayAdapter<String> adapter) {
		databaseReference.child(child)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						adapter.clear();
						for (DataSnapshot dsp : dataSnapshot.getChildren()) {
							adapter.add(String.valueOf(dsp.getValue()));
						}
						adapter.notifyDataSetChanged();
						Log.d(TAG, "onDataChange: " + child + " success");
					}

					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
						Log.d(TAG, "onCancelled: cancelled " + child);
					}
				});
	}

	public void persistOrders(String toString, List<Order> orders, OnWholesaleErrorListener listener) {
		databaseReference.child("orders")
				.child(Instant.now().toString().replace(".", ""))
				.setValue(orders).addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				listener.onSuccess();
			} else {
				listener.onOrderPersistError();
			}
		});
	}
}



