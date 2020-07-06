package com.pzm.pizzera.leaves;

import com.pzm.pizzera.LeaveModel;
import com.pzm.pizzera.UserModel;

import java.util.ArrayList;

public class LeavesPresenter implements LeavesInteractor.OnUsersListFinishedListener{
	private LeavesView leavesView;
	private LeavesInteractor leavesInteractor;

	public LeavesPresenter(LeavesView leaveView, LeavesInteractor leaveInteractor) {
		this.leavesView = leaveView;
		this.leavesInteractor = leaveInteractor;
	}
	@Override
	public void onDatabaseError() {
		leavesView.setDatabaseError();
	}

	@Override
	public void onAdapterSet(ArrayList<LeaveModel> leaves) {
		leavesView.setAdapter(leaves);
	}
	public void setLeavesList() {
		leavesInteractor.getLeavesFromDatabase(this);
	}
}
