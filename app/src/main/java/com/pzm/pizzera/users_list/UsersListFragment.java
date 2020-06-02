package com.pzm.pizzera.users_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.stream.Collectors;

public class UsersListFragment extends Fragment implements UsersListView {

	private RecyclerView listView;
	private UserAdapter userAdapter;
	private ArrayList<UserModel> usersList;
	private UsersListPresenter usersPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_users_list, container, false);

		listView = view.findViewById(R.id.workersList);
		listView.setHasFixedSize(false);
		listView.setLayoutManager(new LinearLayoutManager(getContext()));
		userAdapter=new UserAdapter(getContext());
		listView.setAdapter(userAdapter);

		usersPresenter = new UsersListPresenter(this, new UsersListInteractor());
		usersPresenter.setUsersList();

		return view;
	}

	@Override
	public void setAdapter(ArrayList<UserModel> users) {
		usersList = new ArrayList<UserModel>(users);
		userAdapter.updateData(users);
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Cannot retrieve data from database", Toast.LENGTH_LONG).show();
	}
}
