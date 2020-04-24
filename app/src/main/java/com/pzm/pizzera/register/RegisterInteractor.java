package com.pzm.pizzera.register;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class RegisterInteractor {

	final String TAG = "RegisterInteractor";

	interface OnRegisterFinishedListener {
		void onEmailError();

		void onEmailExists();

		void onNameError();

		void onSurnameError();

		void onPhoneNumberError();

		void onSuccess();
	}

	public void register(final String email, final String name, final String surname, final String phoneNumber, final OnRegisterFinishedListener listener) {

		if (TextUtils.isEmpty(name)) {
			listener.onNameError();
			return;
		}
		if (TextUtils.isEmpty(surname)) {
			listener.onSurnameError();
			return;
		}

		if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			listener.onEmailError();
			return;
		}

		if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 9) {
			listener.onPhoneNumberError();
			return;
		}

		HashMap<String, String> map = new HashMap<String, String>() {{
			put("name", name);
			put("surname", surname);
			put("email", email);
			put("phoneNumber", phoneNumber);
		}};

		FirebaseAuth.getInstance()
				.createUserWithEmailAndPassword(email, getSaltString())
				.addOnCompleteListener(
						task -> {
							if (task.isSuccessful()) {
								writeUser(task.getResult().getUser(), map);
								resetPassword(email, listener);
							} else {
								String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
								if (errorCode == "ERROR_EMAIL_ALREADY_IN_USE")
									listener.onEmailExists();
								else
									Log.d(TAG, "onComplete: " + errorCode);
							}
						}
				);
	}

	private void writeUser(FirebaseUser firebaseUser, HashMap<String, String> map) {
		FirebaseDatabase.getInstance().getReference()
				.child("users")
				.child(firebaseUser.getUid())
				.setValue(map)
				.addOnCompleteListener(task -> {
					Log.d(TAG, "writeUser: " + task.isSuccessful());
				});
	}

	private void resetPassword(String email, OnRegisterFinishedListener listener) {
		FirebaseAuth.getInstance().sendPasswordResetEmail(email)
				.addOnCompleteListener(task -> {
					Log.d(TAG, "resetPassword onComplete: " + task.isSuccessful());
					if (task.isSuccessful())
						listener.onSuccess();
				});
	}

	private String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}
}



