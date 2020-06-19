package com.pzm.pizzera.chat.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pzm.pizzera.R;
import com.pzm.pizzera.chat.MessageModel;

import java.util.ArrayList;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
	public static final int MSG_TYPE_LEFT = 0;
	public static final int MSG_TYPE_RIGHT = 1;

	private FirebaseUser currentUser;
	private ArrayList<MessageModel> messages;
	private Context context;
	private String converserImage;
	private String currentUserImage;

	public ConversationAdapter(Context context, String converserImage, String currentUserImage) {
		this.context = context;
		this.messages = new ArrayList<>();
		this.converserImage = converserImage;
		this.currentUserImage = currentUserImage;
		currentUser = FirebaseAuth.getInstance().getCurrentUser();
	}

	@NonNull
	@Override
	public ConversationAdapter.ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view;
		if (viewType == MSG_TYPE_RIGHT)
			view = LayoutInflater.from(context).inflate(R.layout.message_item_right, parent, false);
		else
			view = LayoutInflater.from(context).inflate(R.layout.message_item_left, parent, false);
		return new ConversationAdapter.ConversationViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ConversationAdapter.ConversationViewHolder holder, int position) {
		holder.bindData(messages.get(position));
	}

	@Override
	public int getItemCount() {
		return messages.size();
	}

	public void updateData(ArrayList<MessageModel> messages) {
		this.messages = new ArrayList<>(messages);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		if (messages.get(position).getSendBy().equals(currentUser.getUid()))
			return MSG_TYPE_RIGHT;
		else
			return MSG_TYPE_LEFT;
	}

	class ConversationViewHolder extends RecyclerView.ViewHolder {
		ImageView profileImage;
		TextView messageView;

		public ConversationViewHolder(@NonNull View itemView) {
			super(itemView);
			profileImage = itemView.findViewById(R.id.profile_image);
			messageView = itemView.findViewById(R.id.show_message);
		}

		public void bindData(@NonNull MessageModel message) {
			String text = message.getMessage();
			messageView.setText(text);
			if (!currentUser.getUid().equals(message.getSendBy())) {
				if (converserImage == null)
					profileImage.setImageResource(R.mipmap.ic_launcher);
				else
					Glide.with(context).load(converserImage).into(profileImage);
			}
			else {
				if (currentUserImage == null)
					profileImage.setImageResource(R.mipmap.ic_launcher);
				else
					Glide.with(context).load(currentUserImage).into(profileImage);
			}
		}
	}
}
