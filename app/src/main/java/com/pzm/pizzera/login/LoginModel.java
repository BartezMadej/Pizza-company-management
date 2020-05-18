package com.pzm.pizzera.login;

import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
	private FirebaseAuth fAuth;
	private int loginCounter;
	public final static int MAX_LOGIN = 3;

	LoginModel() {
		fAuth = FirebaseAuth.getInstance();
		loginCounter = 0;
	}

	//Presenter by implementing this interface becomes middleman between model and view (activity)
	interface OnLoginFinishedListener       //interface is in class because is bound to it
	{
		void onEmailError();

		void onPasswordError();

		void onSuccess();

		void onFailed();

		void onLogged();

		void onLocked();
	}

	public boolean validate(final String email, final String password, final OnLoginFinishedListener listener) {
		if (TextUtils.isEmpty(email)) {
			listener.onEmailError();
			return false;
		}
		if (TextUtils.isEmpty(password) || password.length() < 6) {
			listener.onPasswordError();
			return false;
		}
		return true;
	}

	public void login(final String email, final String password, final boolean ifChecked, final OnLoginFinishedListener listener) {
		fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()) {
					listener.onSuccess();
					rememberUserData(email, password, ifChecked);
				} else {
					listener.onFailed();
					++loginCounter;
					if (loginCounter >= MAX_LOGIN)
						listener.onLocked();
				}
			}
		});
	}

	public void alreadyLogged(final OnLoginFinishedListener listener) {
		FirebaseUser user = fAuth.getCurrentUser();
		if (user != null)
			listener.onLogged();
	}

	private void rememberUserData(final String email, final String password, boolean ifChecked) {
		if (ifChecked) {
			SharedPreferences.Editor editor = LoginFragment.mPrefs.edit();
			editor.putString("user_email", email);
			editor.putString("user_password", password);
			editor.apply();
		} else
			LoginFragment.mPrefs.edit().clear().apply();
	}

}
