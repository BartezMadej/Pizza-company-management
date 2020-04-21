package com.pzm.pizzera.register;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegisterInteractor {

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

		FirebaseAuth.getInstance()
				.createUserWithEmailAndPassword(email, getSaltString())
				.addOnCompleteListener(
						task -> {
							if (task.isSuccessful()) {
//									listener.onSuccess();
								resetPassword(email, listener);
							} else {
								String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
								if (errorCode == "ERROR_EMAIL_ALREADY_IN_USE")
									listener.onEmailExists();
								else
									Log.d("RegisterInteractor", "onComplete: " + errorCode);
							}
						}
				);
	}

	private void resetPassword(String email, OnRegisterFinishedListener listener) {
		FirebaseAuth.getInstance().sendPasswordResetEmail(email)
				.addOnCompleteListener(task -> {
					if (task.isSuccessful())
						listener.onSuccess();
					Log.d("RegisterInteractor", "resetPassword onComplete: " + task.isSuccessful());
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



