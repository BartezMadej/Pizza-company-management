package com.pzm.pizzera.profile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.UserModel;
import com.pzm.pizzera.UserRole;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

@SuppressLint("Registered")
public class EditProfileFragment extends BaseFragment {

	private ImageView close, image_profile;
	private TextView save, tv_change;
	private MaterialEditText name, surname, phone, email, salary, role;

	private FirebaseUser firebaseUser;
	private StorageReference storageRef;

	private StorageTask uploadTask;
	private Uri imageUri;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
		close = view.findViewById(R.id.close);
		image_profile = view.findViewById(R.id.image_profile);
		tv_change = view.findViewById(R.id.tv_change);
		save = view.findViewById(R.id.save);

		name = view.findViewById(R.id.name);
		surname = view.findViewById(R.id.surname);
		phone = view.findViewById(R.id.phone);
		email = view.findViewById(R.id.email);
		salary = view.findViewById(R.id.salary);
		role = view.findViewById(R.id.role);

		firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

		DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
		storageRef = FirebaseStorage.getInstance().getReference("uploads");

		reference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				UserModel user = dataSnapshot.getValue(UserModel.class);
				if (user != null) {
					if (user.getRole() == UserRole.MANAGER || user.getRole() == UserRole.OWNER){
						role.setVisibility(View.VISIBLE);
						salary.setVisibility(View.VISIBLE);
						salary.setText(user.getSalary());
						role.setText(user.getRole().toString());
					}

					name.setText(user.getName());
					surname.setText(user.getSurname());
					phone.setText(user.getPhoneNumber());
					email.setText(user.getEmail());
					if (getActivity().getApplicationContext() != null) {
						Glide.with(getActivity().getApplicationContext()).load(user.getPhoto()).into(image_profile);
					}
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});


		close.setOnClickListener(view1 -> getActivity().finish());

		tv_change.setOnClickListener(view12 -> CropImage.activity()
				.setAspectRatio(1,1)
				.setCropShape(CropImageView.CropShape.OVAL)
				.start(getContext(), EditProfileFragment.this));

		image_profile.setOnClickListener(v -> CropImage.activity()
				.setAspectRatio(1,1)
				.setCropShape(CropImageView.CropShape.OVAL)
				.start(getContext(), EditProfileFragment.this));

		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				updateProfile(name.getText().toString(),
						surname.getText().toString(),
						phone.getText().toString(),
						email.getText().toString(),
						salary.getText().toString(),
						role.getText().toString());

				Toast.makeText(getActivity(), "SAVED", Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}

	private void updateProfile(String name, String surname, String phone, String email, String salary, String role){
		DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
		UserModel model = UserModel.builder()
				.name(name.toString())
				.surname(surname.toString())
				.email(email.toString())
				.role(UserRole.USER)
				.phoneNumber(phone.toString())
				.salary(salary)
				.build();
		reference.setValue(model);
	}

	private String getFileExtension(Uri uri){
		ContentResolver contentResolver = getActivity().getContentResolver();
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
		return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
	}

	private void uploadImage(){
		ProgressDialog pd = new ProgressDialog(getContext());
		pd.setMessage("Uploading");
		pd.show();

		if(imageUri != null){
			StorageReference filereference = storageRef.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

			uploadTask = filereference.putFile(imageUri);
			uploadTask.continueWithTask((Continuation) task -> {
				if (!task.isSuccessful()){
					throw task.getException();
				}
				return filereference.getDownloadUrl();
			}).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
				if(task.isSuccessful()){
					Uri downloadUri = (Uri)task.getResult();
					assert downloadUri != null;
					String myUrl = downloadUri.toString();

					DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());

					HashMap<String, Object> hashMap = new HashMap<>();
					hashMap.put("imageurl", ""+myUrl);
					reference.updateChildren(hashMap);
					pd.dismiss();
				}
			}).addOnFailureListener((OnFailureListener) e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == getActivity().RESULT_OK){
			CropImage.ActivityResult result = CropImage.getActivityResult(data);
			assert result != null;
			imageUri = result.getUri();
			uploadImage();
			Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
		}
	}

}
