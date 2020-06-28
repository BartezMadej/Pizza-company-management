package com.pzm.pizzera.salaries;

import com.pzm.pizzera.UserModel;

import java.util.ArrayList;

public interface SalariesView {
	void setAdapter(ArrayList<UserModel> users);
	void setDatabaseError();
}
