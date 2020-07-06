package com.pzm.pizzera.scheduler.timechooser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;

public class TimeChooserFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_time_chooser, container, false);
		TextView textView = view.findViewById(R.id.test);
		textView.setText(requireArguments().getString("week", ""));

		return view;
	}
}
