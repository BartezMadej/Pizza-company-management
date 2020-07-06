package com.pzm.pizzera.leaves.approve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.LeaveModel;
import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.leave.DatePickerFragment;
import com.pzm.pizzera.leave.LeaveInteractor;
import com.pzm.pizzera.leave.LeavePresenter;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApproveLeaveFragment extends BaseFragment implements ApproveLeaveView{
	private ApproveLeavePresenter presenter;
	private ApproveLeaveInteractor interactor;
	private TextView from;
	private TextView to;
	private TextView userName;
	private TextView userSurname;
	private String userId;
	private String from2;
	private String to2;
	private UserModel user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_approveleave, container, false);
		from = view.findViewById(R.id.from2);
		to = view.findViewById(R.id.to2);
		userName = view.findViewById(R.id.name2);
		userSurname = view.findViewById(R.id.surname2);

		view.findViewById(R.id.buttonApprove).setOnClickListener(v -> approve());

		presenter = new ApproveLeavePresenter(this, new ApproveLeaveInteractor());
		interactor = new ApproveLeaveInteractor();
		Bundle bundle = this.getArguments();

		if(bundle != null) {
			userId = bundle.getString("userId","-1");
			from2 = bundle.getString("from","-1");
			to2 = bundle.getString("to","-1");
		}

		from.setText(from2);
		to.setText(to2);
		presenter.getUserData(userId);
		view.findViewById(R.id.buttonApprove).setOnClickListener(v -> approve());

		return view;
	}

	@Override
	public void setAdapter(UserModel user) {
		this.user = user;

		userName.setText(user.getName());
		userSurname.setText(user.getSurname());
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Cannot retrieve data from database", Toast.LENGTH_LONG).show();
	}

	@Override
	public void approve() {
		interactor.approve(userId, from2, to2);
	}
}
