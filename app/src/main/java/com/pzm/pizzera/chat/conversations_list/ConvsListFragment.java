package com.pzm.pizzera.chat.conversations_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pzm.pizzera.R;

import java.util.ArrayList;

public class ConvsListFragment extends Fragment implements ConvsListView {
	private RecyclerView conversationsView;
	private ConvsListAdapter convsListAdapter;
	private ConvsListPresenter listPresenter;
	public ConvsListFragment() {
	}

	public static ConvsListFragment newInstance() {
		return new ConvsListFragment();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_conversations_list, container, false);
		conversationsView=view.findViewById(R.id.conversationsList);
		conversationsView.setHasFixedSize(false);
		conversationsView.setLayoutManager(new LinearLayoutManager(getContext()));
		convsListAdapter=new ConvsListAdapter(getContext());
		conversationsView.setAdapter(convsListAdapter);
		listPresenter= new ConvsListPresenter(new ConvsListInteractor(), this);
		listPresenter.setConverastions();
		return view;
	}

	@Override
	public void setDataBaseError() {
		Toast.makeText(getContext(),"Cannot retrieve data from firebase",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setConvsList(ArrayList<ConvModel> convs) {
		convsListAdapter.updateData(convs);
	}
}
