package com.pzm.pizzera.wholesale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;

import java.util.ArrayList;

public class WholesaleFragment extends BaseFragment implements WholesaleView {

	final String TAG = "WholesaleFragment";

	private EditText productAmount;
	Spinner vendorsSpinner;
	Spinner productsSpinner;
	ListView orderList;

	private ArrayAdapter<String> vendorsAdapter;
	private ArrayAdapter<String> productsAdapter;
	private ArrayAdapter<String> ordersAdapter;


	private WholesalePresenter presenter;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wholesale, container, false);

		productAmount = view.findViewById(R.id.textProductAmount);
		vendorsSpinner = view.findViewById(R.id.vendorsList);
		productsSpinner = view.findViewById(R.id.productsList);
		orderList = view.findViewById(R.id.orderList);

		vendorsAdapter = getArrayAdapter();
		productsAdapter = getArrayAdapter();
		ordersAdapter = getArrayAdapter();

		vendorsSpinner.setAdapter(vendorsAdapter);
		productsSpinner.setAdapter(productsAdapter);
		orderList.setAdapter(ordersAdapter);

		view.findViewById(R.id.buttonAddProduct).setOnClickListener(v -> addOrder());
		view.findViewById(R.id.buttonApplyOrder).setOnClickListener(v -> sendOrder());

		presenter = new WholesalePresenter(this, new WholesaleInteractor(vendorsAdapter, productsAdapter));
		return view;
	}


	@Override
	public void setAmountError() {
		productAmount.setError("Can not be empty");
	}

	@Override
	public void showProgress() {
		showProgressBar();
	}

	@Override
	public void hideProgress() {
		hideProgressBar();
	}

	private ArrayAdapter<String> getArrayAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1, new ArrayList<String>());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	private void addOrder() {

		presenter.onOrderAdd(
				productAmount.getText(),
				productsSpinner.getSelectedItem(),
				ordersAdapter);
	}

	private void sendOrder() {
		presenter.onOrderSend(vendorsSpinner.getSelectedItem());
	}

}

