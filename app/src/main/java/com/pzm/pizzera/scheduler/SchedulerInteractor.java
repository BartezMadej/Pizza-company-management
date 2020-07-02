package com.pzm.pizzera.scheduler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

public class SchedulerInteractor extends BaseFragment {

	final String TAG = "SchedulerFragment";

	private DatabaseReference dataRef;

	SchedulerInteractor() {
		String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
		dataRef = FirebaseDatabase.getInstance().getReference("scheduler").child(id);
	}

	interface OnSchedulerFinishedListener {
		void onDatabaseError();
		void onTimeInterval();
	}

	public TimeIntervalModel getTimesFromDatabase() {
		TimeIntervalModel times = new TimeIntervalModel();
		ValueEventListener valueEventListener = new ValueEventListener() {
			Map<String, String> tmp = new TreeMap<>();
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
					tmp.put(postSnapshot.getKey(), postSnapshot.getValue().toString());
				}
				times.setTimes(tmp);
			}
			@Override
			public void onCancelled(@NotNull DatabaseError databaseError){
			}
		};
		dataRef.addListenerForSingleValueEvent(valueEventListener);
		return times;
	}
}
