package com.pzm.pizzera.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends BaseFragment implements ProfileView {

	private TextView Name;
	private TextView Surname;
	private TextView Phone;
	private TextView Email;
	private TextView Salary;
	private TextView Role;
	private CircleImageView Image;

	private DatabaseReference reference;
	private FirebaseUser user;

	private ProfilePresenter presenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_profile, container, false);
		Name = view.findViewById(R.id.name);
		Surname = view.findViewById(R.id.surname);
		Phone = view.findViewById(R.id.phone);
		Email = view.findViewById(R.id.email);
		Salary = view.findViewById(R.id.salary);
		Role = view.findViewById(R.id.role);
		Image = view.findViewById(R.id.image);

		if (getArguments() != null) {
			String id = getArguments().getString("userId");
			reference = FirebaseDatabase.getInstance().getReference("users").child(id);
		} else {
			user = FirebaseAuth.getInstance().getCurrentUser();
			reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
		}
		reference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				UserModel user = dataSnapshot.getValue(UserModel.class);
//				assert profile != null;
				if (user != null) {
					Name.setText(user.getName());
					Surname.setText(user.getSurname());
					Phone.setText(user.getPhoneNumber());
					Email.setText(user.getEmail());
					Salary.setText(user.getSalary());
					Role.setText(user.getRole().toString());
					if (user.getPhoto() != null) {
						Glide.with(requireContext()).load(user.getPhoto()).into(Image);
					} else {
						//TODO: load default profile picture
					}
				} else {
					Log.d("ProfileFragment", "onDataChange: null user!");
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
		presenter = new ProfilePresenter(this, new ProfileInteractor());

		return view;
	}

	@Override
	public void setNameError() {
		Name.setError("Invalid name");
	}

	@Override
	public void setSurnameError() {
		Surname.setError("Invalid surname");
	}

	@Override
	public void setPhoneError() {
		Phone.setError("Invalid phone");
	}

	@Override
	public void setEmailError() {
		Email.setError("Invalid email");
	}

	@Override
	public void setSalaryError() {
		Salary.setError("Invalid salary");
	}

	@Override
	public void setRoleError() {
		Role.setError("Invalid role");
	}

}
