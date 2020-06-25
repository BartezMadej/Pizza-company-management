package com.pzm.pizzera.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.profile.ProfileFragment;

public class LoginFragment extends BaseFragment implements LoginView {
	final String TAG = "LoginFragment";

	private EditText textViewEmail;
	private EditText textViewPassword;
	private Button buttonLogin;
	private ProgressBar progressBar;
	private LoginPresenter loginPresenter;
	private CountDownTimer countDownTimer;
	private TextView textViewTimer;
	private CheckBox checkboxRemember;
	public static SharedPreferences dataPrefs;
	public final static String PREFS_NAME = "UserData";
	public final static int TIME_TO_WAIT = 30000;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_login, container, false);

		textViewEmail = view.findViewById(R.id.email);
		textViewPassword = view.findViewById(R.id.password);
		progressBar = view.findViewById(R.id.progressBar);
		buttonLogin = view.findViewById(R.id.loginButton);
		textViewTimer = view.findViewById(R.id.timerText);
		checkboxRemember = view.findViewById(R.id.checkBoxRememberMe);
		dataPrefs = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

		getPreferenceData();
		loginPresenter = new LoginPresenter(this, new LoginInteractor());
		loginPresenter.checkIfAlreadyLogged();
		buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				validateCredential();
			}
		});

		return view;
	}

	@Override
	public void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void setEmailError() {
		textViewEmail.setError("Invalid address email");
	}

	@Override
	public void setPasswordError() {
		textViewPassword.setError("Invalid password");
	}

	@Override
	public void reportFailure() {
		Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void reportSuccess() {
		Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void startTimer() {
		buttonLogin.setEnabled(false);
		textViewTimer.setVisibility(View.VISIBLE);
		countDownTimer = new CountDownTimer(TIME_TO_WAIT, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				textViewTimer.setText(getString(R.string.await, millisUntilFinished / 1000));
			}

			@Override
			public void onFinish() {
				textViewTimer.setVisibility(View.INVISIBLE);
				buttonLogin.setEnabled(true);
			}
		}.start();
	}

	@Override
	public void navigateToHome() {
		Log.d(TAG, "navigateToHome: logged as: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
		Fragment fragment = new ProfileFragment();
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	private void validateCredential() {
		loginPresenter.validateCredentials(textViewEmail.getText().toString().trim(),
				textViewPassword.getText().toString().trim(), checkboxRemember.isChecked());
	}

	private void getPreferenceData() {
		SharedPreferences prefs = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		if (prefs.contains("user_email")) {
			String prefsEmail = prefs.getString("user_email", "");
			textViewEmail.setText(prefsEmail.toString());
		}
		if (prefs.contains("user_password")) {
			String prefsPassword = prefs.getString("user_password", "");
			textViewPassword.setText(prefsPassword.toString());
		}
		checkboxRemember.setChecked(true);
	}
}

