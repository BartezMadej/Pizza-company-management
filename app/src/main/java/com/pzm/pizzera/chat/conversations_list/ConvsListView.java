package com.pzm.pizzera.chat.conversations_list;

import java.util.ArrayList;

public interface ConvsListView {
	void setDataBaseError();
	void setConvsList(ArrayList<ConvModel> convs);
}
