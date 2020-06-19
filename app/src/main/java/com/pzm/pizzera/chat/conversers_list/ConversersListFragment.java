package com.pzm.pizzera.chat.conversers_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.users_list.UserAdapter;
import com.pzm.pizzera.users_list.UsersListInteractor;
import com.pzm.pizzera.users_list.UsersListPresenter;
import com.pzm.pizzera.users_list.UsersListView;

import java.util.ArrayList;

public class ConversersListFragment extends Fragment implements UsersListView {
	private RecyclerView recyclerList;
	private ConverserAdapter converserAdapter;
	private ArrayList<UserModel> usersList;
	private UsersListPresenter usersPresenter;

	public static ConversersListFragment newInstance() {
		return new ConversersListFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_users_list, container, false);

		recyclerList = view.findViewById(R.id.workersList);
		recyclerList.setHasFixedSize(false);
		recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
		converserAdapter=new ConverserAdapter(getContext());
		recyclerList.setAdapter(converserAdapter);

		usersPresenter = new UsersListPresenter(this, new UsersListInteractor());
		usersPresenter.setUsersList();

		return view;
	}

	@Override
	public void setAdapter(ArrayList<UserModel> users) {
		usersList = new ArrayList<UserModel>(users);
		converserAdapter.updateData(users);
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Cannot retrieve data from database", Toast.LENGTH_LONG).show();
	}
}
