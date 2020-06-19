package com.pzm.pizzera.chat.messages;

import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.chat.MessageModel;

import java.util.ArrayList;

public class ConversationPresenter implements ConversationInteractor.OnConverserListFinishedListener {
	private ConversationView messageView;
	private ConversationInteractor messageInteractor;

	public ConversationPresenter(ConversationView messageView, ConversationInteractor messageInteractor) {
		this.messageView = messageView;
		this.messageInteractor = messageInteractor;
	}

	public void setConverser(String uid) {
		messageInteractor.getConverser(uid, this);
	}

	public void setChatId(String uid) {
		messageInteractor.getChatID(uid, this);
	}

	public void sendMessage(String chatId, String text) {
		messageInteractor.sendMessage(chatId,text, this);
	}
	public void setMessagesList(String chatID)
	{
		messageInteractor.getMessagesFromDatabase(chatID,this);
	}
	public void setCurrentUserPhoto()
	{
		messageInteractor.getCurrentUserPhoto(this);
	}
	@Override
	public void onDatabaseError() {
		messageView.setDataBaseError();
	}

	@Override
	public void onConverserSet(UserModel user) {
		messageView.setConverserDisplay(user);
	}

	@Override
	public void onAdapterSet(ArrayList<MessageModel> messages) {
		messageView.setAdapter(messages);
	}

	@Override
	public void onChatIdSet(String chatId) {
		messageView.setChatId(chatId);
	}

	@Override
	public void onCurrentUserPhoto(String photo) {
		messageView.setCurrentUserPhoto(photo);
	}

}
