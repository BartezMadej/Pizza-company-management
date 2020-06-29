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
		presenter = new EditSalaryPresenter(this, new EditSalaryInteractor());

		user = FirebaseAuth.getInstance().getCurrentUser();
		reference = FirebaseDatabase.getInstance().getReference("users");
		Bundle bundle = this.getArguments();
		buttonUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { presenter.updateUserData(reference,salary.getText().toString(), bonus.getText().toString(), userId); }
		});

		if(bundle != null) {
			userId = bundle.getString("userId","-1");
		}
		presenter.getUserData(reference,userId);

		return view;
	}

	@Override
	public void switchView() {
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

	@Override
	public void updateUserInfo(String name, String surname, String salary, String bonus, String photo) {
		this.name.setText(name);
		this.surname.setText(surname);
		this.salary.setText(salary);
		this.bonus.setText(bonus);
		if(photo != null )
			Glide.with(requireContext()).load(photo).into(image);
	}

}
