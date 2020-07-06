package com.pzm.pizzera.orders_history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.pzm.pizzera.R;
import com.pzm.pizzera.orders_history.order_display.OrderDisplayFragment;

import java.util.ArrayList;


public class OrdersHistoryFragment extends Fragment implements OrdersHistoryView {

	private ListView ordersList;
	private ArrayList<String> idsList;
	private ArrayList<String> ordersText;
	private OrdersHistoryPresenter presenter;

	public OrdersHistoryFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_orders_history, container, false);
		ordersList = view.findViewById(R.id.ordersHist);
		ordersText = new ArrayList<String>();
		presenter = new OrdersHistoryPresenter(this, new OrdersHistoryInteractor());
		presenter.setOrders();
		onOrderClick();
		return view;
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setAdapter(ArrayList<String> orders) {
		for (String order : orders) {
			String str = order.substring(0, 16);
			ordersText.add("Order from " + str.replace('T', ' '));
		}
		idsList = new ArrayList<>(orders);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ordersText);
		ordersList.setAdapter(adapter);
	}
	private void onOrderClick()
	{
		ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("orderId",idsList.get(position));
				Fragment fragment = new OrderDisplayFragment();
				fragment.setArguments(bundle);
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.fragment_container, fragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
	}
}
