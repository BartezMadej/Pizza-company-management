package com.pzm.pizzera.chat.conversations_list;

import java.util.ArrayList;

public class ConvsListPresenter implements ConvsListInteractor.OnConversationsListFinishedListener {
	ConvsListView convsListView;
	ConvsListInteractor convsListInteractor;

	ConvsListPresenter(ConvsListInteractor interactor, ConvsListView listView) {
		convsListInteractor = interactor;
		convsListView = listView;
	}
	void setConverastions()
	{
		convsListInteractor.getConversationsList(this);
	}

	@Override
	public void onDataBaseError() {
		convsListView.setDataBaseError();
	}

	@Override
	public void onConversationList(ArrayList<ConvModel> convs) {
		convsListView.setConvsList(convs);
	}
}
