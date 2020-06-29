package com.pzm.pizzera.edit_salary;

import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.UserModel;

import lombok.NonNull;

public class EditSalaryInteractor extends UserModel {

	interface OnValidate {
		void onSalaryError();
		void onBonusError();
	}

	public void validate(String salary, String bonus, OnValidate listener) {
		String regexStr = "^[1-9]+$";
		if (!salary.matches(regexStr)) {
			listener.onSalaryError();
			return;
		}

		if(!bonus.matches(regexStr)) {
			listener.onBonusError();
			return;
		}
	}

	public void updateUserData(DatabaseReference reference, String salary, String bonus, String userId) {
		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot ds : dataSnapshot.getChildren()) {
					UserModel val = new UserModel();
					UserModel uid = ds.getValue(UserModel.class);
					if( uid.getId().equalsIgnoreCase(userId)) {
						uid.setSalary(salary);
						uid.setBonus(bonus);
						reference.child(uid.getId()).setValue(uid);
					}

				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				return;
			}
		};
		reference.addListenerForSingleValueEvent(valueEventListener);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
