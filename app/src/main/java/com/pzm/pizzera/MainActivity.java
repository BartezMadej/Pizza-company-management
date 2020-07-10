package com.pzm.pizzera;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.chat.ChatFragment;
import com.pzm.pizzera.leaves.LeavesFragment;
import com.pzm.pizzera.login.LoginFragment;
import com.pzm.pizzera.orders_history.OrdersHistoryFragment;
import com.pzm.pizzera.profile.EditProfileFragment;
import com.pzm.pizzera.profile.ProfileFragment;
import com.pzm.pizzera.register.RegisterFragment;
import com.pzm.pizzera.salaries.SalariesFragment;
import com.pzm.pizzera.scheduler.SchedulerFragment;
import com.pzm.pizzera.scheduler.TimeIntervalModel;
import com.pzm.pizzera.users_list.UsersListFragment;
import com.pzm.pizzera.wholesale.WholesaleFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private DrawerLayout drawerLayout;
	private Menu navMenu;
	private UserRole role;
	private final String TAG = "MainActivity";
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
			case "Profile":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new ProfileFragment()).commit();
				break;
			case "Edit Profile":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new EditProfileFragment()).commit();
				break;
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
			case "Orders History":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new OrdersHistoryFragment()).commit();
				break;
			case "Leaves":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new LeavesFragment()).commit();
				break;
			case "Salaries":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new SalariesFragment()).commit();
				break;
			case "Wholesale":
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new WholesaleFragment()).commit();
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

		DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users");
		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				UserRole userRole = dataSnapshot.getValue(UserModel.class).getRole();

				if(firebaseUser!=null){

					navMenu.findItem(R.id.nav_login).setVisible(false);
					navMenu.findItem(R.id.nav_register).setVisible(false);

					navMenu.findItem(R.id.nav_logout).setVisible(true);
					navMenu.findItem(R.id.nav_chat).setVisible(true);
					navMenu.findItem(R.id.nav_schedule).setVisible(true);
					navMenu.findItem(R.id.nav_profile).setVisible(true);
					navMenu.findItem(R.id.nav_edit_profile).setVisible(true);

					if(userRole==UserRole.MANAGER || userRole==UserRole.OWNER)
					{
						navMenu.findItem(R.id.nav_workers_list).setVisible(true);
						navMenu.findItem(R.id.nav_orders_history).setVisible(true);
						navMenu.findItem(R.id.nav_leaves).setVisible(true);
						navMenu.findItem(R.id.nav_salaries).setVisible(true);
						navMenu.findItem(R.id.nav_wholesale).setVisible(true);
					}
				}
				else{
					navMenu.findItem(R.id.nav_login).setVisible(true);
					navMenu.findItem(R.id.nav_register).setVisible(true);

					navMenu.findItem(R.id.nav_logout).setVisible(false);
					navMenu.findItem(R.id.nav_chat).setVisible(false);
					navMenu.findItem(R.id.nav_workers_list).setVisible(false);
					navMenu.findItem(R.id.nav_schedule).setVisible(false);
					navMenu.findItem(R.id.nav_workers_list).setVisible(false);
					navMenu.findItem(R.id.nav_orders_history).setVisible(false);
					navMenu.findItem(R.id.nav_leaves).setVisible(false);
					navMenu.findItem(R.id.nav_salaries).setVisible(false);
					navMenu.findItem(R.id.nav_profile).setVisible(false);
					navMenu.findItem(R.id.nav_edit_profile).setVisible(false);
					navMenu.findItem(R.id.nav_wholesale).setVisible(false);
				}

			}
			@Override
			public void onCancelled(@NotNull DatabaseError databaseError){
			}
		};
		dataRef.addValueEventListener(valueEventListener);
	}
}
