package com.pzm.pizzera.chat.messages;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.chat.ChatModel;
import com.pzm.pizzera.chat.MessageModel;

import java.util.ArrayList;
import java.util.Calendar;


public class ConversationInteractor {
	private ArrayList<MessageModel> messagesList = new ArrayList<MessageModel>();
	private FirebaseUser currentUser;
	private Calendar calendar;

	public ConversationInteractor() {
		currentUser = FirebaseAuth.getInstance().getCurrentUser();
		calendar = Calendar.getInstance();
	}

	interface OnConverserListFinishedListener {
		void onDatabaseError();

		void onConverserSet(UserModel user);

		void onAdapterSet(ArrayList<MessageModel> messages);

		void onChatIdSet(String chatId);

		void onCurrentUserPhoto(String photo);
	}

	public void getConverser(String uid, final OnConverserListFinishedListener listener) {
		DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
		dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				UserModel user = dataSnapshot.getValue(UserModel.class);
				listener.onConverserSet(user);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}

	public void getCurrentUserPhoto(final OnConverserListFinishedListener listener) {
		DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("photo");
		dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				String photo = dataSnapshot.getValue(String.class);
				listener.onCurrentUserPhoto(photo);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}

	public void getChatID(String uid, final OnConverserListFinishedListener listener) {
		Query ref = FirebaseDatabase.getInstance().getReference("usersChats").child(uid);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (!dataSnapshot.exists()) {
					listener.onChatIdSet(createNewChat(uid));
					return;
				}
				ArrayList<String> list = new ArrayList<>();
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					String chatStr = data.getValue(String.class);
					list.add(chatStr);
				}
				findChatId(uid, list, listener);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}

	private void findChatId(String uid, ArrayList<String> currentUserChatList, final OnConverserListFinishedListener listener) {
		Query ref = FirebaseDatabase.getInstance().getReference("usersChats").child(currentUser.getUid());
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					for (DataSnapshot data : dataSnapshot.getChildren()) {
						String chatStr = data.getValue(String.class);
						if (currentUserChatList.contains(chatStr)) {
							listener.onChatIdSet(chatStr);
							return;
						}
					}
				}
				listener.onChatIdSet(createNewChat(uid));
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}

	private String createNewChat(String uid) {
		ArrayList<String> chatters = new ArrayList<>();
		chatters.add(uid);
		chatters.add(currentUser.getUid());

		ChatModel model = new ChatModel();
		model.setMembers(chatters);
		model.setLastMessage("");

		DatabaseReference pushedRef = FirebaseDatabase.getInstance().getReference("Chats").push();
		String chatId = pushedRef.getKey();
		pushedRef.setValue(model);

		DatabaseReference usersChatsRef = FirebaseDatabase.getInstance().getReference("usersChats");
		usersChatsRef.child(uid).push().setValue(chatId);
		usersChatsRef.child(currentUser.getUid()).push().setValue(chatId);
		return chatId;
	}

	public void sendMessage(String chatId, String messageText, final OnConverserListFinishedListener listener) {
		DatabaseReference chatMessagesRef = FirebaseDatabase.getInstance().getReference("chatMessages").child(chatId).push();
		MessageModel message = new MessageModel();
		message.setSendBy(currentUser.getUid());
		message.setMessage(messageText);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		message.setMessageDate(dayOfMonth + ":" + (month + 1) + ":" + (year));

		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		message.setMessageTime(hours + ":" + minutes);
		chatMessagesRef.setValue(message);

		DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats").child(chatId).child("lastMessage");
		chatsRef.setValue(message.getMessage() + " " + message.getMessageTime());
	}

	public void getMessagesFromDatabase(String chatId,
	                                    final OnConverserListFinishedListener listener) {
		DatabaseReference chatMessagesRef = FirebaseDatabase.getInstance().getReference("chatMessages").child(chatId);
		chatMessagesRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (!dataSnapshot.exists())
					return;
				messagesList.clear();
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					MessageModel textMessage = data.getValue(MessageModel.class);
					if (textMessage != null)
						messagesList.add(textMessage);
				}
				listener.onAdapterSet(messagesList);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDatabaseError();
			}
		});
	}
}
