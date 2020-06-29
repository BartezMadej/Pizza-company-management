package com.pzm.pizzera.edit_salary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.salaries.SalariesFragment;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.NonNull;

public class EditSalaryFragment extends BaseFragment implements EditSalaryView {
	private TextView name;
	private TextView surname;
	private TextView salary;
	private Button buttonUpdate;

	private CircleImageView image;
	private TextView bonus;
	private String userId;

	private DatabaseReference reference;
	private FirebaseUser user;

	private EditSalaryPresenter presenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_salary, container, false);
		name = view.findViewById(R.id.name);
		surname = view.findViewById(R.id.surname);
		salary = view.findViewById(R.id.salary);
		image = view.findViewById(R.id.image);
		bonus = view.findViewById(R.id.bonus);
		buttonUpdate = view.findViewById(R.id.buttonUpdate);

		user = FirebaseAuth.getInstance().getCurrentUser();
		reference = FirebaseDatabase.getInstance().getReference("users");
		Bundle bundle = this.getArguments();
		buttonUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { switchView(v); }
		});

		if(bundle != null) {
			userId = bundle.getString("userId","-1");
		}
		reference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for( DataSnapshot ds : dataSnapshot.getChildren()) {

					UserModel profile = ds.getValue(UserModel.class);
					if(profile.getId().equalsIgnoreCase(userId)){
					assert profile != null;
					name.setText(profile.getName());
					surname.setText(profile.getSurname());
					salary.setText(profile.getSalary());
					bonus.setText(profile.getBonus());
					if(profile.getPhoto() != null )
						Glide.with(requireContext()).load(profile.getPhoto()).into(image);
					}

				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				return;
			}
		});
		presenter = new EditSalaryPresenter(this, new EditSalaryInteractor());

		return view;
	}

	private void switchView(View v) {
		ValueEventListener valueEventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot ds : dataSnapshot.getChildren()) {
					UserModel val = new UserModel();
					UserModel uid = ds.getValue(UserModel.class);
					if( uid.getId().equalsIgnoreCase(userId)) {
						uid.setSalary(salary.getText().toString());
						uid.setBonus(bonus.getText().toString());
						reference.child(uid.getId()).setValue(uid);
					}

				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				return;
			}
		};
		reference.addListenerForSingleValueEvent(valueEventListener);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SalariesFragment fragment = new SalariesFragment();
		FragmentManager manager;
		try {
			manager = getActivity().getSupportFragmentManager();
		} catch (NullPointerException exception) {
			Log.d("SalariesFragment", "onUserClicked", exception);
			return;
		}

		manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
	}

	@Override
	public void setSalaryError() {
		salary.setError("Invalid salary");
	}

	@Override
	public void setBonusError() {
		bonus.setError("Invalid bonus");
	}
}
