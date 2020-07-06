package com.pzm.pizzera.leaves;

import com.pzm.pizzera.LeaveModel;
import com.pzm.pizzera.UserModel;

import java.util.ArrayList;

public interface LeavesView {
	void setAdapter(ArrayList<LeaveModel> leaves);
	void setDatabaseError();
}
