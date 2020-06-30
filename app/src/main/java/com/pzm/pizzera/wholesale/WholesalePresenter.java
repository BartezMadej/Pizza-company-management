package com.pzm.pizzera.wholesale;

import android.text.Editable;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class WholesalePresenter implements WholesaleInteractor.OnWholesaleErrorListener {
	private final String TAG = "WholesalePresenter";

	private final WholesaleView wholesaleView;
	private final WholesaleInteractor wholesaleInteractor;
	private List<Order> orders;

	WholesalePresenter(WholesaleView wholesaleView, WholesaleInteractor wholesaleInteractor) {
		this.wholesaleView = wholesaleView;
		this.wholesaleInteractor = wholesaleInteractor;
		this.orders = new ArrayList<>();
	}

	@Override
	public void onAmountError() {
		wholesaleView.setAmountError();
	}

	@Override
	public void onOrderPersistError() {
		wholesaleView.hideProgress();
	}

	@Override
	public void onSuccess() {
		wholesaleView.hideProgress();
		Log.d(TAG, "onSuccess: ordered");
	}


	public void onOrderAdd(Editable amount, Object product, ArrayAdapter<String> ordersAdapter) {
		try {
			if (!amount.toString().matches(".*\\d.*")) {
				throw new NullPointerException();
			}
			Order order = new Order(amount.toString(), product.toString());
			ordersAdapter.add(order.toString());
			orders.add(order);
			ordersAdapter.notifyDataSetChanged();
		} catch (NullPointerException e) {
			onAmountError();
		}
	}

	public void onOrderSend(Object vendor) {
		try {
			wholesaleView.showProgress();
			wholesaleInteractor.persistOrders(vendor.toString(), orders, this);
		} catch (Exception e) {
			Log.d(TAG, "onOrderSend: " + e.getMessage());
		}
	}
}
