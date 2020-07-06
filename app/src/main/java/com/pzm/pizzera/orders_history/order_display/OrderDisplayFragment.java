package com.pzm.pizzera.orders_history.order_display;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pzm.pizzera.R;
import com.pzm.pizzera.wholesale.Order;

import java.util.ArrayList;


public class OrderDisplayFragment extends Fragment implements OrderDisplayView {
	private String orderId;
	private OrderDisplayPresenter presenter;
	private TextView title;
	private ListView itemsList;

	public static OrderDisplayFragment newInstance(String param1, String param2) {
		return new OrderDisplayFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		View view = inflater.inflate(R.layout.fragment_order_display, container, false);
		if (bundle == null)
			return view;

		orderId = bundle.getString("orderId");
		presenter = new OrderDisplayPresenter(this, new OrderDisplayInteractor());
		presenter.getOrder(orderId);

		title = view.findViewById(R.id.orderTitle);
		itemsList = view.findViewById(R.id.orderItems);
		String str = orderId.substring(0, 16);
		str = str.replace('T', ' ');
		title.setText(str);
		return view;
	}

	@Override
	public void setDatabaseError() {
		Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setAdapter(ArrayList<Order> orderItems) {
		ArrayAdapter<Order> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,orderItems);
		itemsList.setAdapter(adapter);
	}
}
