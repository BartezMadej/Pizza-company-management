package com.pzm.pizzera.register;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.pzm.pizzera.UserModel;

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


	public void register(UserModel user, final OnRegisterFinishedListener listener) {

		if (validateUser(user, listener)) {
			return;
		}

		FirebaseAuth.getInstance()
				.createUserWithEmailAndPassword(user.getEmail(), getSaltString())
				.addOnCompleteListener(
						task -> {
							if (task.isSuccessful()) {
								FirebaseUser firebaseUser = task.getResult().getUser();
								user.setId(firebaseUser.getUid());
								writeUser(firebaseUser, user);
								resetPassword(user.getEmail(), listener);
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

	private boolean validateUser(UserModel user, OnRegisterFinishedListener listener) {
		if (TextUtils.isEmpty(user.getName())) {
			listener.onNameError();
			return true;
		}
		if (TextUtils.isEmpty(user.getSurname())) {
			listener.onSurnameError();
			return true;
		}

		if (TextUtils.isEmpty(user.getEmail()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
			listener.onEmailError();
			return true;
		}

		if (TextUtils.isEmpty(user.getPhoneNumber()) || user.getPhoneNumber().length() != 9) {
			listener.onPhoneNumberError();
			return true;
		}
		return false;
	}

	private void writeUser(FirebaseUser firebaseUser, UserModel user) {
		FirebaseDatabase.getInstance().getReference()
				.child("users")
				.child(firebaseUser.getUid())
				.setValue(user)
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



