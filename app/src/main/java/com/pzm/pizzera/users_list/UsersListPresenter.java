package com.pzm.pizzera.users_list;

import com.pzm.pizzera.UserModel;

import java.util.ArrayList;

public class UsersListPresenter implements UsersListInteractor.OnUsersListFinishedListener {
	private UsersListView usersListView;
	private UsersListInteractor usersListInteractor;

	public UsersListPresenter(UsersListView usersListView, UsersListInteractor usersListInteractor) {
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