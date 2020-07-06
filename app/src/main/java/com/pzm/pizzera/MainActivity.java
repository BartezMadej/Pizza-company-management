package com.pzm.pizzera;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.pzm.pizzera.chat.ChatFragment;
import com.pzm.pizzera.login.LoginFragment;
import com.pzm.pizzera.register.RegisterFragment;
import com.pzm.pizzera.scheduler.SchedulerFragment;
import com.pzm.pizzera.users_list.UsersListFragment;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private DrawerLayout drawerLayout;
	private Menu navMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		drawerLayout = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		navMenu = navigationView.getMenu();

		FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
			displayMenu(firebaseAuth.getCurrentUser());
		};

		FirebaseAuth.getInstance().addAuthStateListener(authStateListener);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
				toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch(item.toString()){
			case "Login":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new LoginFragment()).commit();
				break;
			case "Register":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new RegisterFragment()).commit();
				break;
			case "Logout":
				FirebaseAuth.getInstance().signOut();
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new LoginFragment()).commit();
				break;

			case "Workers list":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new UsersListFragment()).commit();
				break;
			case "Chat":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new ChatFragment()).commit();
				break;
			case "Schedule":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new SchedulerFragment()).commit();
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

	private void displayMenu(FirebaseUser firebaseUser){

		if(firebaseUser!=null){

			navMenu.findItem(R.id.nav_login).setVisible(false);
			navMenu.findItem(R.id.nav_register).setVisible(false);

			navMenu.findItem(R.id.nav_logout).setVisible(true);
			navMenu.findItem(R.id.nav_chat).setVisible(true);
			navMenu.findItem(R.id.nav_workers_list).setVisible(true);
			navMenu.findItem(R.id.nav_schedule).setVisible(true);
		}
		else{
			navMenu.findItem(R.id.nav_login).setVisible(true);
			navMenu.findItem(R.id.nav_register).setVisible(true);

			navMenu.findItem(R.id.nav_logout).setVisible(false);
			navMenu.findItem(R.id.nav_chat).setVisible(false);
			navMenu.findItem(R.id.nav_workers_list).setVisible(false);
			navMenu.findItem(R.id.nav_schedule).setVisible(false);
		}
	}

}
