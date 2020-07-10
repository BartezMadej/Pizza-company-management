package com.pzm.pizzera.scheduler.timechooser;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.scheduler.TimeIntervalModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TimeChooserFragment extends BaseFragment implements View.OnClickListener {


	final private String TAG = "TimeChooserFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_time_chooser, container, false);
		TextView textView = view.findViewById(R.id.test);
		String week = requireArguments().getString("week", "");
		textView.setText(week);

		TextView textFrom = view.findViewById(R.id.timeFrom);
		TextView textTo = view.findViewById(R.id.timeTo);
		Button newHours = view.findViewById(R.id.addNewHours);

		Date currentTime = Calendar.getInstance().getTime();
		String tmp2 = currentTime.toString().split(" ")[3];
		String time = tmp2.split(":")[0] + ":" + tmp2.split(":")[1];

		textFrom.setText(time);
		textTo.setText(time);

		textFrom.setOnClickListener(this);
		textTo.setOnClickListener(this);

		SwipeMenuListView listView = view.findViewById(R.id.timeIntervals);
		List<String> strs = new ArrayList<>();

		for(int i=0;i<20;i++){
			@SuppressLint("DefaultLocale") String tmp = String.format("time interval %d", i);
			strs.add(tmp);
		}

		ArrayAdapter<String>  listAdapter = new ArrayAdapter<String>(getLayoutInflater().getContext(), android.R.layout.simple_list_item_1, strs);
		listView.setAdapter(listAdapter);

		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getContext());
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xE0,
						0xE0, 0xE0)));
				deleteItem.setWidth(170);
				deleteItem.setIcon(R.drawable.ic_action_name);
				menu.addMenuItem(deleteItem);
			}
		};

		listView.setMenuCreator(creator);

		listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				if (index == 1) {
				}
				return false;
			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.timeFrom:
				DialogFragment timePickerFrom = new TimePickerFragment();
				timePickerFrom.show(getChildFragmentManager(),"time picker");
				break;
			case R.id.timeTo:
				DialogFragment timePickerTo = new TimePickerFragment();
				timePickerTo.show(getChildFragmentManager(),"time picker");
				break;

			case R.id.addNewHours:

				break;
		}
	}
}
