package com.pzm.pizzera.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pzm.pizzera.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private TextView mName;
    private TextView mSurname;
    private TextView mPhone;
    private TextView mEmail;
    private TextView mSalary;
    private TextView mRole;
    private CircleImageView mImage;

    private DatabaseReference reference;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mName = view.findViewById(R.id.name);
        mSurname = view.findViewById(R.id.surname);
        mPhone = view.findViewById(R.id.phone);
        mEmail = view.findViewById(R.id.email);
        mSalary = view.findViewById(R.id.salary);
        mRole = view.findViewById(R.id.role);
        mImage = view.findViewById(R.id.image);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileModel profile = dataSnapshot.getValue(ProfileModel.class);
                assert profile != null;
                mName.setText(profile.getName());
                mSurname.setText(profile.getSurname());
                mPhone.setText(profile.getPhone());
                mEmail.setText(profile.getEmail());
                mSalary.setText(profile.getSalary());
                mRole.setText(profile.getRole());
                Glide.with(getContext()).load(profile.getPhoto()).into(mImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
