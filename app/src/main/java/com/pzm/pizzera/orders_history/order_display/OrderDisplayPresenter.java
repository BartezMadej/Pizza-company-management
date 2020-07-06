package com.pzm.pizzera.orders_history.order_display;

import com.pzm.pizzera.wholesale.Order;

import java.util.ArrayList;

public class OrderDisplayPresenter implements OrderDisplayInteractor.OnOrderDisplayListener {
	private OrderDisplayView orderDisplayView;
	private OrderDisplayInteractor orderDisplayInteractor;

	OrderDisplayPresenter(OrderDisplayView view, OrderDisplayInteractor interactor)
	{
		orderDisplayView=view;
		orderDisplayInteractor=interactor;
	}
	void getOrder(String orderId)
	{
		orderDisplayInteractor.getOrderItems(this,orderId);
	}


	@Override
	public void onDatabaseError() {

	}

	@Override
	public void onAdapterSet(ArrayList<Order> orderItems) {
		orderDisplayView.setAdapter(orderItems);
	}
}
