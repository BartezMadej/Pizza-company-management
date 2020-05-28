package com.pzm.pizzera.users_list;

import com.pzm.pizzera.UserModel;

import java.util.ArrayList;

public interface UsersListView {
	void setAdapter(ArrayList<UserModel> users);

	void setDatabaseError();
}
