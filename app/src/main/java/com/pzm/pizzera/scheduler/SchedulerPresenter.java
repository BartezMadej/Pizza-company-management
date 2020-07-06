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

	public void setTimeValues(){
		schedulerInteractor.getTimesFromDatabase(this);
	}

	@Override
	public void onDatabaseError() {
		schedulerView.setDatabaseError();
	}

	@Override
	public void onTimeInterval(TimeIntervalModel times){
		schedulerView.setTimeInterval(times);
	}
}
