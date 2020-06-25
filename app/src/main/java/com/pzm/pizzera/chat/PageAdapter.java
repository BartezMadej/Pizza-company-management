package com.pzm.pizzera.chat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pzm.pizzera.chat.conversations_list.ConvsListFragment;
import com.pzm.pizzera.chat.conversers_list.ConversersListFragment;
import com.pzm.pizzera.home.HomeFragment;
import com.pzm.pizzera.users_list.UsersListFragment;


public class PageAdapter extends FragmentStateAdapter {
	private static final int SIZE=2;
	PageAdapter(Fragment fragment) {
		super(fragment);
	}

	@NonNull
	@Override
	public Fragment createFragment(int position) {
		if (position == 0)
			return new ConvsListFragment();
		else
			return new ConversersListFragment();
	}

	@Override
	public int getItemCount() {
		return SIZE;
	}
}
