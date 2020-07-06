package com.pzm.pizzera.leaves.approve;

import com.pzm.pizzera.UserModel;

public interface ApproveLeaveView {
	void setAdapter(UserModel user);
	void setDatabaseError();
	void approve();
}
