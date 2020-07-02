package com.pzm.pizzera.scheduler;

public class SchedulerPresenter implements
		SchedulerInteractor.OnSchedulerFinishedListener {

	private SchedulerView schedulerView;
	private SchedulerInteractor schedulerInteractor;

	public SchedulerPresenter(SchedulerView schedulerView,
							  SchedulerInteractor schedulerInteractor){
		this.schedulerInteractor = schedulerInteractor;
		this.schedulerView = schedulerView;
	}

	@Override
	public void onDatabaseError() {
		schedulerView.setDatabaseError();
	}

	@Override
	public void onTimeInterval(){
		schedulerView.setTimeInterval(schedulerInteractor.getTimesFromDatabase());
	}
}
