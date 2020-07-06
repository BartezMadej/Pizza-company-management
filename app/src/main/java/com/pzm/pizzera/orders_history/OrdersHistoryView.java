package com.pzm.pizzera.orders_history;

import java.util.ArrayList;

public interface OrdersHistoryView {
	void setDatabaseError();
	void setAdapter(ArrayList<String> orders);
}
