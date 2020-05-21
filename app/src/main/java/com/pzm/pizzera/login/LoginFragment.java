package com.pzm.pizzera.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.pzm.pizzera.BaseFragment;
import com.pzm.pizzera.R;
import com.pzm.pizzera.home.HomeFragment;
import com.pzm.pizzera.profile.ProfileFragment;

public class LoginFragment extends BaseFragment implements LoginView {

    private EditText mTextViewEmail;
    private EditText mTextViewPassword;
    private Button mButtonLogin;
    private ProgressBar mProgressBar;
    private LoginPresenter mPresenter;
    private CountDownTimer mCountDownTimer;
    private TextView mTextViewTimer;
    private CheckBox mCheckboxRemember;
    public static SharedPreferences mPrefs;
    public final static String PREFS_NAME = "UserData";
    public final static int TIME_TO_WAIT = 30000;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container,false);

        mTextViewEmail = view.findViewById(R.id.email);
        mTextViewPassword = view.findViewById(R.id.password);
        mProgressBar = view.findViewById(R.id.progressBar);
        mButtonLogin = view.findViewById(R.id.loginButton);
        mTextViewTimer = view.findViewById(R.id.timerText);
        mCheckboxRemember = view.findViewById(R.id.checkBoxRememberMe);
        mPrefs = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        getPreferenceData();
        mPresenter = new LoginPresenter(this, new LoginModel());
        mPresenter.checkIfAlreadyLogged();
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCredential();
            }
        });

        return view;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setEmailError() {
        mTextViewEmail.setError("Invalid address email");
    }

    @Override
    public void setPasswordError() {
        mTextViewPassword.setError("Invalid password");
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
        mButtonLogin.setEnabled(false);
        mTextViewTimer.setVisibility(View.VISIBLE);
        mCountDownTimer = new CountDownTimer(TIME_TO_WAIT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextViewTimer.setText(getString(R.string.await, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mTextViewTimer.setVisibility(View.INVISIBLE);
                mButtonLogin.setEnabled(true);
            }
        }.start();
    }

    @Override
    public void navigateToHome() {
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTextViewEmail = null;
        mPresenter = null;
        mButtonLogin = null;
        mProgressBar = null;
        mTextViewTimer = null;
        mCountDownTimer = null;
        mCheckboxRemember = null;
        mPrefs = null;
    }

    private void validateCredential() {
        mPresenter.validateCredentials(mTextViewEmail.getText().toString().trim(),
                mTextViewPassword.getText().toString().trim(), mCheckboxRemember.isChecked());
    }

    private void getPreferenceData() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.contains("user_email")) {
            String prefsEmail = prefs.getString("user_email", "");
            mTextViewEmail.setText(prefsEmail.toString());
        }
        if (prefs.contains("user_password")) {
            String prefsPassword = prefs.getString("user_password", "");
            mTextViewPassword.setText(prefsPassword.toString());
        }
        mCheckboxRemember.setChecked(true);
    }
}

