package com.pzm.pizzera.profile;

import android.os.Bundle;
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileInteractor profile = dataSnapshot.getValue(ProfileInteractor.class);
                assert profile != null;
                Name.setText(profile.getName());
                Surname.setText(profile.getSurname());
                Phone.setText(profile.getPhone());
                Email.setText(profile.getEmail());
                Salary.setText(profile.getSalary());
                Role.setText(profile.getRole());
                Glide.with(getContext()).load(profile.getPhoto()).into(Image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        presenter = new ProfilePresenter(this, new ProfileInteractor());

        return view;
    }

    @Override
    public void setNameError(){Name.setError("Invalid name");}

    @Override
    public void setSurnameError(){Surname.setError("Invalid surname");}

    @Override
    public void setPhoneError(){Phone.setError("Invalid phone");}

    @Override
    public void setEmailError(){Email.setError("Invalid email");}

    @Override
    public void setSalaryError(){Salary.setError("Invalid salary");}

    @Override
    public void setRoleError(){Role.setError("Invalid role");}

}
