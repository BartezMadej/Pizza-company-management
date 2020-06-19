package com.pzm.pizzera;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pzm.pizzera.login.LoginFragment;
import com.pzm.pizzera.profile.ProfileFragment;
import com.pzm.pizzera.register.RegisterFragment;

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

		FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
				Context context = getApplicationContext();

				if(firebaseUser!=null){
					navMenu.findItem(R.id.nav_login).setVisible(false);
					navMenu.findItem(R.id.nav_register).setVisible(false);
					navMenu.findItem(R.id.nav_logout).setVisible(true);
				}
				else{
					navMenu.findItem(R.id.nav_logout).setVisible(false);
					navMenu.findItem(R.id.nav_login).setVisible(true);
					navMenu.findItem(R.id.nav_register).setVisible(true);
				}
			}
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
