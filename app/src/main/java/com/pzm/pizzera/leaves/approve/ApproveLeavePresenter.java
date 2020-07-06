package com.pzm.pizzera.leaves.approve;

import com.pzm.pizzera.UserModel;

public class ApproveLeavePresenter implements ApproveLeaveInteractor.OnLeaveFinishedListener{
	private ApproveLeaveView leaveView;
	private ApproveLeaveInteractor leaveInteractor;

	public ApproveLeavePresenter(ApproveLeaveView leaveView, ApproveLeaveInteractor leaveInteractor) {
		this.leaveView = leaveView;
		this.leaveInteractor = leaveInteractor;
	}

	@Override
	public void onDatabaseError() {
		leaveView.setDatabaseError();
	}

	@Override
	public void onAdapterSet(UserModel user) {
		leaveView.setAdapter(user);
	}
	public void getUserData(String userId) {
		leaveInteractor.getUserData(this,userId);
	}
}
