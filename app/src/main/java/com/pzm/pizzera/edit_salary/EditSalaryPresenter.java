package com.pzm.pizzera.edit_salary;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.UserModel;

import lombok.Data;
import lombok.NonNull;

public class EditSalaryPresenter implements EditSalaryInteractor.OnValidate{
	private EditSalaryView profileView;
	private EditSalaryInteractor profileInteractor;

	public EditSalaryPresenter(EditSalaryView profileView, EditSalaryInteractor profileInteractor) {
		this.profileView = profileView;
		this.profileInteractor = profileInteractor;
	}


	public void validateCredentials(String salary, String bonus) {
		profileInteractor.validate(salary, bonus, this);
	}

	public void getUserData(DatabaseReference reference, String userId) {
		reference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for( DataSnapshot ds : dataSnapshot.getChildren()) {
					UserModel profile = ds.getValue(UserModel.class);
					if(profile.getId().equalsIgnoreCase(userId)){
						assert profile != null;
						profileView.updateUserInfo(profile.getName(), profile.getSurname(), profile.getSalary(), profile.getBonus(),profile.getPhoto());
					}
				}
			}
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				return;
			}
		});

	}

	public void updateUserData(DatabaseReference reference, String salary, String bonus, String userId) {
		profileInteractor.updateUserData(reference,salary,bonus,userId);
		profileView.switchView();
	}
	@Override
	public void onSalaryError() {
		if (profileView != null) {
			profileView.setSalaryError();
		}
	}

	@Override
	public void onBonusError() {
		if (profileView != null) {
			profileView.setBonusError();
		}
	}
}
