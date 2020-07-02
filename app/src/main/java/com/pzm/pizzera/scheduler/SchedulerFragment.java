package com.pzm.pizzera.scheduler;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.scheduler.timechooser.TimeChooserFragment;


public class SchedulerFragment extends BaseFragment
		implements SchedulerView {

	final private String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday"};
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_scheduler, container, false);
		onDayClicked();
		SchedulerPresenter schedulerPresenter = new SchedulerPresenter(this, new SchedulerInteractor());
		schedulerPresenter.onTimeInterval();

		return view;
	}

	private void onDayClicked(){
		Resources res = getResources();
		for(String s: days) {
			int id = res.getIdentifier(s.substring(0,3), "id", getContext().getPackageName());
			view.findViewById(id).setOnClickListener(v -> redirectToTimeChooser(s));
		}
	}

	private void redirectToTimeChooser(String week) {
		Fragment fragment = new TimeChooserFragment();
		Bundle args = new Bundle();
		args.putString("week", week);
		fragment.setArguments(args);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Cannot retrieve data from database",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void setTimeInterval(TimeIntervalModel times){

	}
}
