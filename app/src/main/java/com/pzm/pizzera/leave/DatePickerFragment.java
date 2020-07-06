package com.pzm.pizzera.leave;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.pzm.pizzera.R;

import org.w3c.dom.Text;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

	private TextView from;
	private TextView to;
	private boolean flag;

	DatePickerFragment(TextView from, TextView to, boolean flag) {
		this.from = from;
		this.to = to;
		this.flag = flag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener dateSetListener =
			new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int day) {
					String day1;
					String month1;
					String year1;

					if(view.getDayOfMonth() > 9 )
						day1 = Integer.toString(view.getDayOfMonth());
					else
						day1 = "0" + Integer.toString(view.getDayOfMonth());

					if(view.getMonth() > 8 )
						month1 = Integer.toString((view.getMonth()+1));
					else
						month1 = "0" + Integer.toString((view.getMonth()+1));

					year1 = Integer.toString(view.getYear());
					if(flag)
						from.setText(  day1 + "." + month1 + "." + year1 );
					else
						to.setText(  day1 + "." + month1 + "." + year1 );
					Toast.makeText(getActivity(), "selected date is " + view.getYear() +
							" / " + (view.getMonth()+1) +
							" / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
				}
			};
}
