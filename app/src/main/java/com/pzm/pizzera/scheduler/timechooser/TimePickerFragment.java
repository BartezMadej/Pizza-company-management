package com.pzm.pizzera.scheduler.timechooser;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.pzm.pizzera.scheduler.TimeIntervalModel;

import java.net.InterfaceAddress;
import java.util.Calendar;
import java.util.Objects;

import com.pzm.pizzera.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		return new TimePickerDialog(getActivity(),
				this,
				hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
	}


	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		TextView textFrom = getActivity().findViewById(R.id.timeFrom);
		TextView textTo = getActivity().findViewById(R.id.timeTo);

		textFrom.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
	}
}
