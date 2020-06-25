package com.pzm.pizzera.chat.conversations_list;

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

import java.util.ArrayList;

public class ConvsListInteractor {
	private FirebaseUser currentUser;
	private ArrayList<ConvModel> convsList;

	interface OnConversationsListFinishedListener {
		void onDataBaseError();

		void onConversationList(ArrayList<ConvModel> convs);

	}

	ConvsListInteractor() {
		currentUser = FirebaseAuth.getInstance().getCurrentUser();
		convsList = new ArrayList<ConvModel>();
	}

	void getConversationsList(final OnConversationsListFinishedListener listener) {
		Query query = FirebaseDatabase.getInstance().getReference("usersChats").child(currentUser.getUid());
		query.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (!dataSnapshot.exists())
					return;
				ArrayList<String> chatsIdList = new ArrayList<String>();
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					String chat = data.getValue(String.class);
					chatsIdList.add(chat);
				}
				getUserChats(chatsIdList, listener);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDataBaseError();
			}
		});
	}

	private void getUserChats(ArrayList<String> chatsIdList, final OnConversationsListFinishedListener listener) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats");
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (!dataSnapshot.exists())
					return;
				ArrayList<ChatModel> chats = new ArrayList<ChatModel>();
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					String key = data.getKey();
					if (chatsIdList.contains(key)) {
						ChatModel chat = data.getValue(ChatModel.class);
						if (chat != null && !chat.getLastMessage().equals(""))
							chats.add(chat);

					}
				}
				getUsersData(chats, listener);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDataBaseError();
			}
		});
	}

	private void getUsersData(ArrayList<ChatModel> chats, final OnConversationsListFinishedListener listener) {
		ArrayList<String> usersID = new ArrayList<String>();
		for (ChatModel model : chats) {
			if (model.getMembers().get(0).equals(currentUser.getUid()))
				usersID.add(model.getMembers().get(1));
			else
				usersID.add(model.getMembers().get(0));
		}

		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					if (data != null && usersID.contains(data.getKey())) {
						UserModel user = data.getValue(UserModel.class);
						ConvModel conv = new ConvModel();
						conv.setUid(user.getId());
						conv.setPhoto(user.getPhoto());
						conv.setName(user.getName());
						conv.setSurname(user.getSurname());
						conv.setLastMessage(findLastMessage(chats,user.getId()));
						convsList.add(conv);
					}
				}
				listener.onConversationList(convsList);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				listener.onDataBaseError();
			}
		});
	}
	private String findLastMessage(ArrayList<ChatModel> chats,String userID)
	{
		for (ChatModel chat : chats) {
			if (chat.getMembers().get(0).equals(userID) || chat.getMembers().get(1).equals(userID))
				return chat.getLastMessage();

		}
		return "";
	}
}
