package com.pzm.pizzera.users_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.profile.ProfileFragment;

import java.util.ArrayList;

public class UsersListFragment extends Fragment implements UsersListView {

	private ListView listView;
	private ArrayAdapter<String> listAdapter;

	private ArrayList<String> usersNames;
	private ArrayList<UserModel> usersList;

	private UsersListPresenter usersPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_users_list, container, false);
		listView = view.findViewById(R.id.workersList);
		usersPresenter = new UsersListPresenter(this, new UsersListInteractor());
		usersPresenter.setUsersList();
		onUserClicked();
		return view;
	}

	@Override
	public void setAdapter(ArrayList<UserModel> users) {
		usersList = new ArrayList<UserModel>(users);

		usersNames = new ArrayList<String>();
		for (int i = 0; i < usersList.size(); ++i)
			usersNames.add(usersList.get(i).getSurname()+" "+usersList.get(i).getName());
		listAdapter = new ArrayAdapter<String>(getLayoutInflater().getContext(), android.R.layout.simple_list_item_1, usersNames);
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
				ProfileFragment fragment = new ProfileFragment();
				fragment.setArguments(bundle);
				FragmentManager manager;
				try {
					manager = getActivity().getSupportFragmentManager();
				} catch (NullPointerException exception) {
					Log.d("UsersListFragment", "onUserClicked", exception);
					return;
				}
				manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
			}
		});
	}
}