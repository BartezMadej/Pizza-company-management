package com.pzm.pizzera.chat.conversations_list;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pzm.pizzera.R;
import com.pzm.pizzera.chat.messages.ConversationFragment;

import java.util.ArrayList;

public class ConvsListAdapter extends RecyclerView.Adapter<ConvsListAdapter.ConversationViewHolder> {
	private ArrayList<ConvModel> convs;
	private Context context;

	public ConvsListAdapter(Context context) {
		this.context = context;
		this.convs = new ArrayList<>();
	}

	@NonNull
	@Override
	public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.conversation_item, parent, false);
		return new ConversationViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
		holder.bindData(convs.get(position));
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("userId", convs.get(position).getUid());
				ConversationFragment fragment = new ConversationFragment();
				fragment.setArguments(bundle);
				AppCompatActivity acv = (AppCompatActivity) v.getContext();
				acv.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
			}
		});
	}

	@Override
	public int getItemCount() {
		return convs.size();
	}

	public void updateData(ArrayList<ConvModel> convs) {
		this.convs = convs;
		notifyDataSetChanged();
	}

	class ConversationViewHolder extends RecyclerView.ViewHolder {
		private TextView convName;
		private ImageView convImage;
		private TextView convMessage;

		public ConversationViewHolder(@NonNull View itemView) {
			super(itemView);
			convImage = itemView.findViewById(R.id.conversationPhoto);
			convName = itemView.findViewById(R.id.conversationName);
			convMessage = itemView.findViewById(R.id.conversationLastMessage);
		}

		public void bindData(ConvModel conv) {
			if (conv == null)
				return;
			String fullName = conv.getName() + " " + conv.getSurname();
			convName.setText(fullName);
			if (conv.getLastMessage().length() > 20) {
				String firstWord = conv.getLastMessage().substring(0, conv.getLastMessage().indexOf(' '));
				String lastWord = conv.getLastMessage().substring(conv.getLastMessage().lastIndexOf(' ') + 1);
				convMessage.setText(firstWord + " " + lastWord);
			} else
				convMessage.setText(conv.getLastMessage());
			if (conv.getPhoto() == null)
				convImage.setImageResource(R.mipmap.ic_launcher);
			else
				Glide.with(context).load(conv.getPhoto()).into(convImage);
		}
	}
}
