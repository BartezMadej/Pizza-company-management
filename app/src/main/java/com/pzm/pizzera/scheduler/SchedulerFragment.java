package com.pzm.pizzera.scheduler;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.leave.LeaveFragment;
import com.pzm.pizzera.scheduler.timechooser.TimeChooserFragment;

import java.util.Map;

public class SchedulerFragment extends BaseFragment
		implements SchedulerView {

	final private String TAG = "SchedulerFragment";
	final private String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday"};
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_scheduler, container, false);
		onDayClicked();
		SchedulerPresenter schedulerPresenter =
				new SchedulerPresenter(this, new SchedulerInteractor());
		schedulerPresenter.setTimeValues();

		Button btn = view.findViewById(R.id.makeLeave);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new LeaveFragment();
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.fragment_container, fragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});

		return view;
	}

	private void onDayClicked(){
		Resources res = getResources();
		for(String s: days) {
			int id = res.getIdentifier(s.substring(0,3),
					"id", getContext().getPackageName());
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
		Map<String, String> intervals = times.getTimes();
		Resources res = getResources();
		for (Map.Entry<String,String> entry : intervals.entrySet()){
			int id = res.getIdentifier(entry.getKey(), "id", getContext().getPackageName());
			String[] inter = entry.getValue().split(",");
			RelativeLayout layout = view.findViewById(id);
			for(String s: inter){
				String[] t = s.split("-");
				int first_min = 60*Integer.parseInt(t[0].split(":")[0])
						+ Integer.parseInt(t[0].split(":")[1]);
				int second_min = 60*Integer.parseInt(t[1].split(":")[0])
						+ Integer.parseInt(t[1].split(":")[1]);
				if(first_min<second_min){
					addTile(layout, first_min, second_min);
				}
				else{
					addTile(layout, first_min, 1440);
					addTile(layout, 0, second_min);
				}
			}
		}
	}

	private void addTile(RelativeLayout layout, int first_min, int second_min) {
		View v = new View(getActivity());
		v.setBackgroundResource(R.color.medium_dark_gray);
		float factor = getContext().getResources().getDisplayMetrics().density;
		RelativeLayout.LayoutParams params = new
				RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				(int)(factor*(second_min - first_min)));
		params.setMargins(0,(int)(factor*first_min),0,0);
		layout.addView(v, params);
	}
}
