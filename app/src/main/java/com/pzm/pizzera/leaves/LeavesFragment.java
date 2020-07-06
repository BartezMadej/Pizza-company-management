package com.pzm.pizzera.leaves;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.LeaveModel;
import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.leaves.approve.ApproveLeaveFragment;

import java.util.ArrayList;
import java.util.List;

public class LeavesFragment extends BaseFragment implements LeavesView {
	private ListView listView;
	private ArrayAdapter<String> listAdapter;
	private ArrayList<LeaveModel> leaveList;
	private LeavesPresenter leavesPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_leaves, container, false);
		listView = view.findViewById(R.id.leaveList);
		leavesPresenter = new LeavesPresenter(this,new LeavesInteractor());
		leavesPresenter.setLeavesList();
		onLeaveClicked();
		return view;
	}

	@Override
	public void setAdapter(ArrayList<LeaveModel> leaves) {
		leaveList = new ArrayList<LeaveModel>(leaves);
		List<String> usersStr = new ArrayList<>();

		int tmp2 = 0;
		for( LeaveModel leave: leaves) {
			tmp2 += 1;
			String tmp = String.format("%s.	  From: %s To: %s", Integer.toString(tmp2),leave.getFrom(),leave.getTo());
			usersStr.add(tmp);
		}

		listAdapter = new ArrayAdapter<String>(getLayoutInflater().getContext(), android.R.layout.simple_list_item_1, usersStr);
		listView.setAdapter(listAdapter);
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Cannot retrieve data from database", Toast.LENGTH_LONG).show();
	}

	private void onLeaveClicked() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("userId", leaveList.get((int) id).getUserId());
				bundle.putString("from",leaveList.get((int) id).getFrom());
				bundle.putString("to",leaveList.get((int) id).getTo());
				ApproveLeaveFragment fragment = new ApproveLeaveFragment();
				fragment.setArguments(bundle);
				FragmentManager manager;
				try {
					manager = getActivity().getSupportFragmentManager();
				} catch (NullPointerException exception) {
					Log.d("LeavesFragment", "onUserClicked", exception);
					return;
				}
				manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
			}
		});
	}
}
