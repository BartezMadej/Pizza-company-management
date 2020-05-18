package com.pzm.pizzera.register;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.pzm.pizzera.BaseActivity;
import com.pzm.pizzera.R;

public class RegisterActivity extends BaseActivity implements RegisterView {

	final String TAG = "RegisterActivity";

	private ProgressBar progressBar;
	private EditText name;
	private EditText surname;
	private EditText email;
	private EditText phoneNumber;

	private RegisterPresenter presenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		progressBar = findViewById(R.id.registerProgressBar);
		name = findViewById(R.id.fieldName);
		surname = findViewById(R.id.fieldSurname);
		email = findViewById(R.id.fieldEmail);
		phoneNumber = findViewById(R.id.fieldPhoneNumber);

		findViewById(R.id.buttonCreateUser).setOnClickListener(v -> validateForm());

		presenter = new RegisterPresenter(this, new RegisterInteractor());
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
	public void navigateBack() {
		finish();
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
}

