package com.pzm.pizzera.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.pzm.pizzera.R;

public class ChatFragment extends Fragment {
	TabLayout tabLayout;
	ViewPager2 viewPager;
	PageAdapter pageChangerAdapter;
	final static String[] titles = {"Friends", "Chat"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat_layout, container, false);
		pageChangerAdapter = new PageAdapter(this);
		viewPager = view.findViewById(R.id.pager);
		viewPager.setAdapter(pageChangerAdapter);
		tabLayout = view.findViewById(R.id.tab_layout);
		new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();
		return view;
	}
}
