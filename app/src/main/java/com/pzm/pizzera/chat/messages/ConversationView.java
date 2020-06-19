package com.pzm.pizzera.chat.messages;

import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.chat.MessageModel;

import java.util.ArrayList;

public interface ConversationView {
	void setConverserDisplay(UserModel user);

	void setDataBaseError();

	void setAdapter(ArrayList<MessageModel> messages);

	void setChatId(String chatId);

	void setCurrentUserPhoto(String photo);
}
