package com.pzm.pizzera.orders_history.order_display;

import com.pzm.pizzera.wholesale.Order;

import java.util.ArrayList;

public interface OrderDisplayView {
	void setDatabaseError();

	void setAdapter(ArrayList<Order> orderItems);
}
