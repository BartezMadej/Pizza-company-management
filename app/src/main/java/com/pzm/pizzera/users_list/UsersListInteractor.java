package com.pzm.pizzera.users_list;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UsersListInteractor {

	private DatabaseReference dataRef;
	private ArrayList<UserModel> usersList = new ArrayList<UserModel>();

	UsersListInteractor() {
		dataRef = FirebaseDatabase.getInstance().getReference("users");
	}

	interface OnUsersListFinishedListener {
		void onDatabaseError();

		void onAdapterSet(ArrayList<UserModel> uList);
	}

	public void getUsersFromDatabase(final OnUsersListFinishedListener listener) {
		dataRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					UserModel value = data.getValue(UserModel.class);
					if (value != null) {
						value.setId(data.getKey());
						usersList.add(value);
					}
				}
				sortWorkersList();
				listener.onAdapterSet(usersList);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}

	private void sortWorkersList() {
		Collections.sort(usersList, new Comparator<UserModel>() {
			@Override
			public int compare(UserModel o1, UserModel o2) {
				return o1.getSurname().compareToIgnoreCase(o2.getSurname());
			}
		});
	}
}