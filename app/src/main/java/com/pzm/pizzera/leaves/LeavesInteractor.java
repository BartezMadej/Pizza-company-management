package com.pzm.pizzera.leaves;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.LeaveModel;
import com.pzm.pizzera.UserModel;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class LeavesInteractor {
	private DatabaseReference dataRef;
	private ArrayList<LeaveModel> userLeaves = new ArrayList<LeaveModel>();
	private ArrayList<String> userIds = new ArrayList<String>();
	LeavesInteractor() {
		dataRef = FirebaseDatabase.getInstance().getReference("leaves");
	}

	interface OnUsersListFinishedListener {
		void onDatabaseError();
		void onAdapterSet(ArrayList<LeaveModel> userLeaves);
	}

	public void getLeavesFromDatabase(final LeavesInteractor.OnUsersListFinishedListener listener) {
		List<LeaveModel> list = new ArrayList<>();
		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot ds : dataSnapshot.getChildren()) {
					for( DataSnapshot dt : ds.getChildren()) {
						LeaveModel uid = dt.getValue(LeaveModel.class);
						if(uid.getApproved().contains("0"))
							userLeaves.add(uid);
					}
				}
				listener.onAdapterSet(userLeaves);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}
		};
		dataRef.addListenerForSingleValueEvent(valueEventListener);
	}
}
