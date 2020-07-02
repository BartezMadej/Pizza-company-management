package com.pzm.pizzera.scheduler;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scheduler, container, false);

		view.findViewById(R.id.mondayRelativeLayout).
				setOnClickListener(v -> redirectToTimeChooser("monday"));
		view.findViewById(R.id.tuesdayRelativeLayout).
				setOnClickListener(v -> redirectToTimeChooser("tuesday"));
		view.findViewById(R.id.wednesdayRelativeLayout).
				setOnClickListener(v -> redirectToTimeChooser("wednesday"));
		view.findViewById(R.id.thursdayRelativeLayout).
				setOnClickListener(v -> redirectToTimeChooser("thursday"));
		view.findViewById(R.id.fridayRelativeLayout).
				setOnClickListener(v -> redirectToTimeChooser("friday"));

		return view;
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
}
