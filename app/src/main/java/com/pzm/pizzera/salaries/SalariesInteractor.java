package com.pzm.pizzera.salaries;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.users_list.UsersListInteractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.NonNull;

public class SalariesInteractor {
	private DatabaseReference dataRef;
	private ArrayList<UserModel> users = new ArrayList<UserModel>();

	SalariesInteractor() {
		dataRef = FirebaseDatabase.getInstance().getReference("users");
	}

	interface OnUsersListFinishedListener {
		void onDatabaseError();
		void onAdapterSet(ArrayList<UserModel> users);
	}

	public void getUsersFromDatabase(final SalariesInteractor.OnUsersListFinishedListener listener) {
		List<UserModel> list = new ArrayList<>();
		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot ds : dataSnapshot.getChildren()) {
					UserModel val = new UserModel();
					UserModel uid = ds.getValue(UserModel.class);
					if(!list.contains(uid)) {
						if( uid.getSalary() == null) {
							uid.setSalary("0");
							dataRef.child(uid.getId()).setValue(uid);
						}
						if( uid.getBonus() == null ) {
							uid.setBonus("0");
							dataRef.child(uid.getId()).setValue(uid);
						}
						users.add(uid);

					}
				}

				sortWorkersList();
				listener.onAdapterSet(users);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}
		};
		dataRef.addListenerForSingleValueEvent(valueEventListener);
	}

	private void sortWorkersList() {
		Collections.sort(users, new Comparator<UserModel>() {
			@Override
			public int compare(UserModel o1, UserModel o2) {
				return o1.getSurname().compareToIgnoreCase(o2.getSurname());
			}
		});
	}
}
