package com.pzm.pizzera.salaries;

import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.users_list.UsersListInteractor;
import com.pzm.pizzera.users_list.UsersListView;

import java.util.ArrayList;

public class SalariesPresenter implements SalariesInteractor.OnUsersListFinishedListener {
	private SalariesView usersListView;
	private SalariesInteractor usersListInteractor;

	public SalariesPresenter(SalariesView usersListView, SalariesInteractor usersListInteractor) {
		this.usersListView = usersListView;
		this.usersListInteractor = usersListInteractor;
	}

	public void setUsersList() {
		usersListInteractor.getUsersFromDatabase(this);
	}

	@Override
	public void onDatabaseError() {
		usersListView.setDatabaseError();
	}

	@Override
	public void onAdapterSet(ArrayList<UserModel> users) {
		usersListView.setAdapter(users);
	}
}
