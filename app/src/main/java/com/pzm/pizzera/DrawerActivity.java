package com.pzm.pizzera;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private FrameLayout frameLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);

		NavigationView mNavigationView = findViewById(R.id.nav_view);

		if (mNavigationView != null) {
			mNavigationView.setNavigationItemSelectedListener(this);
		}
	}

	private void inflateLayout(int activity) {
		frameLayout = findViewById(R.id.content_frame);
		LayoutInflater layoutInflater =
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View activityView = layoutInflater != null ?
				layoutInflater.inflate(activity, null, false) : null;
		if (activityView != null) {
			frameLayout.addView(activityView);
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();

		if (frameLayout != null) {
			frameLayout.removeViewAt(0);
		}

		switch (id) {
			case R.id.nav_login:
				//inflateLayout(R.layout.activity_login);
				break;
			case R.id.nav_chat:
				//inflateLayout(R.layout.activity_chat);
				break;
			default:
				break;
		}

		return false;
	}
}
