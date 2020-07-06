package com.pzm.pizzera.leave;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.salaries.SalariesFragment;

public class LeaveFragment extends BaseFragment implements LeaveView {
	private TextView from;
	private TextView to;

	private LeavePresenter presenter;
	private LeaveInteractor interactor;
	private Calendar calendar;
	private int year;
	private int month;
	private int day;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_leave, container, false);
		from = view.findViewById(R.id.From);
		to = view.findViewById(R.id.To);
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String strDate = dateFormat.format(date);

		from.setText(strDate);
		to.setText(strDate);

		view.findViewById(R.id.buttonSelectFrom).setOnClickListener(v -> setFrom());
		view.findViewById(R.id.buttonSelectTo).setOnClickListener(v -> setTo());
		view.findViewById(R.id.buttonSet).setOnClickListener(v -> set());

		presenter = new LeavePresenter(this, new LeaveInteractor());
		interactor = new LeaveInteractor();

		return view;
	}

	@Override
	public void set() {
		interactor.insertData(from.getText().toString(), to.getText().toString());
	}

	@Override
	public void setFrom() {
		DialogFragment newFragment = new DatePickerFragment(from,to,true);
		newFragment.show(getChildFragmentManager(),"date picker");
	}

	@Override
	public void setTo() {
		DialogFragment newFragment = new DatePickerFragment(from,to,false);
		newFragment.show(getChildFragmentManager(),"date picker");
	}

}
