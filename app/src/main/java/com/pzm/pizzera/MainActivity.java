package com.pzm.pizzera;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.pzm.pizzera.login.LoginFragment;
import com.pzm.pizzera.register.RegisterFragment;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		drawerLayout = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
				toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch(item.getItemId()){
			case R.id.nav_register:
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new RegisterFragment()).commit();
				break;
			case R.id.nav_login:
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new LoginFragment()).commit();
				break;
		}

		drawerLayout.closeDrawer(GravityCompat.START);

		return true;
	}

	@Override
	public void onBackPressed() {
		if(drawerLayout.isDrawerOpen((GravityCompat.START))){
			drawerLayout.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}
}
