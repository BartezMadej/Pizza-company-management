package com.pzm.pizzera.chat.messages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.chat.MessageModel;

import java.util.ArrayList;

public class ConversationFragment extends Fragment implements ConversationView {
	private ImageView converserPhoto;
	private TextView converserName;
	private RecyclerView messagesListView;
	private ConversationPresenter presenter;

	private EditText messageText;
	private ImageButton messageSendBtn;
	private ConversationAdapter convAdapter;

	private String chatId;
	private String photo;
	private String currentUserPhoto;

	public static ConversationFragment newInstance() {
		return new ConversationFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_conversation, container, false);
		converserPhoto = view.findViewById(R.id.converserPhoto);
		converserName = view.findViewById(R.id.converserName);
		messageText=view.findViewById(R.id.messageText);
		messageSendBtn=view.findViewById(R.id.sendButton);

		messagesListView=view.findViewById(R.id.messagesList);
		messagesListView.setHasFixedSize(false);
		messagesListView.setLayoutManager(new LinearLayoutManager(getContext()));

		presenter = new ConversationPresenter(this, new ConversationInteractor());
		presenter.setCurrentUserPhoto();
		if (getArguments() != null) {
			String converseUid = getArguments().getString("userId");
			presenter.setConverser(converseUid);
			presenter.setChatId(converseUid);

			convAdapter = new ConversationAdapter(getContext(), photo, currentUserPhoto);
			messagesListView.setAdapter(convAdapter);
			onClickSend();
		}
		return view;
	}

	@Override
	public void setConverserDisplay(UserModel user) {
		String fullName = user.getSurname() + " " + user.getName();
		converserName.setText(fullName);
		photo=user.getPhoto();
		if (user.getPhoto() == null)
			converserPhoto.setImageResource(R.mipmap.ic_launcher);
		else
			Glide.with(getContext()).load(user.getPhoto()).into(converserPhoto);
	}

	@Override
	public void setDataBaseError() {
		Toast.makeText(getContext(), "Cannot retrieve data from database", Toast.LENGTH_LONG).show();
	}

	@Override
	public void setAdapter(ArrayList<MessageModel> messages) {
		convAdapter.updateData(messages);
		messagesListView.scrollToPosition(convAdapter.getItemCount()-1);
	}

	@Override
	public void setChatId(String chatId) {
		this.chatId=chatId;
		presenter.setMessagesList(chatId);
	}

	@Override
	public void setCurrentUserPhoto(String photo) {
		currentUserPhoto=photo;
	}

	private void onClickSend()
	{
		messageSendBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String text=messageText.getText().toString();
				if(text.equals(""))
					return;
				presenter.sendMessage(chatId,text);
				messageText.setText("");
			}
		});
	}

}
