package com.pzm.pizzera.leave;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.LeaveModel;
import com.pzm.pizzera.UserModel;

import lombok.NonNull;

public class LeaveInteractor {

	private DatabaseReference reference;
	private FirebaseUser user;

	public void insertData(String from, String to) {
		user = FirebaseAuth.getInstance().getCurrentUser();
		reference = FirebaseDatabase.getInstance().getReference("leaves").child(user.getUid());

		int count = (int)(Math.random()*100000);

		reference = reference.child(Integer.toString(count));
		LeaveModel leave = new LeaveModel(from,to,"0",user.getUid(),Integer.toString(count));
		reference.setValue(leave);
	}

}
