package com.pzm.pizzera.users_list;

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
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.profile.ProfileFragment;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
	private ArrayList<UserModel> users;
	private Context context;

	public UserAdapter(Context context)
	{
		this.context=context;
		this.users = new ArrayList<>();
	}
	@NonNull
	@Override
	public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view= LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
		return new UserAdapter.UserViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
		holder.bindData(users.get(position));
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("userId", users.get(position).getId());
				ProfileFragment fragment = new ProfileFragment();
				fragment.setArguments(bundle);
				AppCompatActivity acv=(AppCompatActivity)v.getContext();
				acv.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
			}
		});
	}

	@Override
	public int getItemCount() {
		return users.size();
	}
	public void updateData(ArrayList<UserModel> users)
	{
		this.users=users;
		notifyDataSetChanged();
	}

	class UserViewHolder extends RecyclerView.ViewHolder
	{
		private TextView userName;
		private ImageView profileImage;
		public UserViewHolder(@NonNull View itemView) {
			super(itemView);
			userName=itemView.findViewById(R.id.userName);
			profileImage=itemView.findViewById(R.id.profile_image);
		}

		public void bindData(UserModel user)
		{
			if(user==null)
				return;
			String name=user.getSurname()+" "+user.getName();
			userName.setText(name);
			if(user.getPhoto()==null)
				profileImage.setImageResource(R.mipmap.ic_launcher);
			else
				Glide.with(context).load(user.getPhoto()).into(profileImage);
		}
	}
}
