package com.pzm.pizzera.orders_history;

import java.util.ArrayList;

public class OrdersHistoryPresenter implements OrdersHistoryInteractor.OnOrdersHistoryListener {
	private OrdersHistoryView ordersHistoryView;
	private OrdersHistoryInteractor ordersHistoryInteractor;

	OrdersHistoryPresenter(OrdersHistoryView view, OrdersHistoryInteractor interactor) {
		ordersHistoryInteractor = interactor;
		ordersHistoryView = view;
	}

	void setOrders() {
		ordersHistoryInteractor.getOrdersIds(this);
	}

	@Override
	public void onDatabaseError() {
		ordersHistoryView.setDatabaseError();
	}

	@Override
	public void onAdapterSet(ArrayList<String> orders) {
		ordersHistoryView.setAdapter(orders);
	}
}
