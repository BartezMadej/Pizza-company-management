package com.pzm.pizzera.leave;

public class LeavePresenter {
	private LeaveView leaveView;
	private LeaveInteractor leaveInteractor;

	public LeavePresenter(LeaveView leaveView, LeaveInteractor leaveInteractor) {
		this.leaveView = leaveView;
		this.leaveInteractor = leaveInteractor;
	}

}
