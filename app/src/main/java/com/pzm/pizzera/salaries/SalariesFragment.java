package com.pzm.pizzera.salaries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.edit_salary.EditSalaryFragment;
import com.pzm.pizzera.profile.ProfileFragment;
import com.pzm.pizzera.users_list.UsersListInteractor;
import com.pzm.pizzera.users_list.UsersListPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SalariesFragment extends Fragment implements SalariesView {
	private ListView listView;
	private ArrayAdapter<String> listAdapter;
	private ArrayList<UserModel> usersList;
	private SalariesPresenter usersPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_salary, container, false);
		listView = view.findViewById(R.id.salaryList);
		usersPresenter = new SalariesPresenter(this, new SalariesInteractor());
		usersPresenter.setUsersList();
		onUserClicked();
		return view;
	}

	@Override
	public void setAdapter(ArrayList<UserModel> users) {
		usersList = new ArrayList<UserModel>(users);
		List<String> usersStr = new ArrayList<>();


		for( UserModel user: users) {
			String tmp = String.format("%s %s Salary: %s Bonus: %s", user.getName(),user.getSurname(),user.getSalary(),user.getBonus());
			usersStr.add(tmp);
		}
		//List<String> usersNames = users.stream().map(user->user.getName()).collect(Collectors.toList());
		//List<String> usersSalaries = users.stream().map(user->user.getSalary()).collect(Collectors.toList());
		//List<String> usersBonuses = users.stream().map(user->user.getBonus()).collect(Collectors.toList());
		//List<String> usersStr = new ArrayList<>();


		listAdapter = new ArrayAdapter<String>(getLayoutInflater().getContext(), android.R.layout.simple_list_item_1, usersStr);
		listView.setAdapter(listAdapter);
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Cannot retrieve data from database", Toast.LENGTH_LONG).show();
	}

	private void onUserClicked() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("userId", usersList.get((int) id).getId());
				EditSalaryFragment fragment = new EditSalaryFragment();
				fragment.setArguments(bundle);
				FragmentManager manager;
				try {
					manager = getActivity().getSupportFragmentManager();
				} catch (NullPointerException exception) {
					Log.d("SalariesFragment", "onUserClicked", exception);
					return;
				}
				manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
			}
		});
	}
}
