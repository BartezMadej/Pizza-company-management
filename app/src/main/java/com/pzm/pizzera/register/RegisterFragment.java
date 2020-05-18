package com.pzm.pizzera.register;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.pzm.pizzera.R;

public class RegisterFragment extends Fragment implements RegisterView {

	final String TAG = "RegisterFragment";

	private ProgressBar progressBar;
	private EditText name;
	private EditText surname;
	private EditText email;
	private EditText phoneNumber;

	private RegisterPresenter presenter;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register, container,false);

		progressBar = view.findViewById(R.id.registerProgressBar);
		name = view.findViewById(R.id.fieldName);
		surname = view.findViewById(R.id.fieldSurname);
		email = view.findViewById(R.id.fieldEmail);
		phoneNumber = view.findViewById(R.id.fieldPhoneNumber);

		view.findViewById(R.id.buttonCreateUser).setOnClickListener(v -> validateForm());

		presenter = new RegisterPresenter(this, new RegisterInteractor());

		return view;
	}

	private void showProgressBar() {
		if (progressBar != null) {
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	private void hideProgressBar() {
		if (progressBar != null) {
			progressBar.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void setEmailError() {
		email.setError("Email address invalid");
	}

	@Override
	public void setEmailExistsError() {
		email.setError("User with given email exists");
	}

	@Override
	public void setNameError() {
		name.setError("Name cannot be empty");
	}

	@Override
	public void setSurnameError() {
		surname.setError("Surname cannot be empty");
	}

	@Override
	public void setPhoneNumberError() {
		phoneNumber.setError("Password cannot be empty");
	}

	@Override
	public void showProgress() {
		showProgressBar();
	}

	@Override
	public void hideProgress() {
		hideProgressBar();
	}

	private void validateForm() {
		Log.d(TAG, "validateForm: validate");
		presenter.validateCredentials(
				name.getText().toString(),
				surname.getText().toString(),
				email.getText().toString(),
				phoneNumber.getText().toString()
		);
	}

	public String getUid() {
		return FirebaseAuth.getInstance().getCurrentUser().getUid();
	}
}

