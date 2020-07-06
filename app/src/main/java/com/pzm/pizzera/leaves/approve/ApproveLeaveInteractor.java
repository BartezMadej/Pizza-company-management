package com.pzm.pizzera.leaves.approve;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.LeaveModel;
import com.pzm.pizzera.UserModel;

import lombok.Data;
import lombok.NonNull;

public class ApproveLeaveInteractor {
	private DatabaseReference referenceLeaves;
	private DatabaseReference referenceUsers;
	private UserModel user;

	interface OnLeaveFinishedListener {
		void onDatabaseError();
		void onAdapterSet(UserModel user);
	}

	ApproveLeaveInteractor() {
		referenceLeaves = FirebaseDatabase.getInstance().getReference("leaves");
		referenceUsers = FirebaseDatabase.getInstance().getReference("users");
	}

	public void getUserData(final ApproveLeaveInteractor.OnLeaveFinishedListener listener, String userId) {
		DatabaseReference referenceUser = referenceUsers.child(userId);
		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				UserModel user2 = dataSnapshot.getValue(UserModel.class);
				listener.onAdapterSet(user2);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}
		};
		referenceUser.addListenerForSingleValueEvent(valueEventListener);
	}

	public void approve(String userId, String from, String to) {
		DatabaseReference referenceLeave = referenceLeaves.child(userId);
		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot ds : dataSnapshot.getChildren()) {
					LeaveModel leave = ds.getValue(LeaveModel.class);
					if(leave.getUserId().contains(userId) && leave.getFrom().contains(from) && leave.getTo().contains(to)) {
						leave.setApproved("1");
						DatabaseReference refTmp = referenceLeave.child(leave.getId());
						refTmp.setValue(leave);
					}
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}
		};
		referenceLeave.addListenerForSingleValueEvent(valueEventListener);
	}
}
