package com.pzm.pizzera.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pzm.pizzera.MainActivity;
import com.pzm.pizzera.R;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private EditText mTextViewEmail;
    private EditText mTextViewPassword;
    private Button mButtonLogin;
    private ProgressBar mProgressBar;
    private LoginPresenter mPresenter;
    private CountDownTimer mCountDownTimer;
    private TextView mTextViewTimer;
    private CheckBox mCheckboxRemember;
    public static SharedPreferences mPrefs;
    public final static String PREFS_NAME="UserData";
    public final static int TIME_TO_WAIT=30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextViewEmail =findViewById(R.id.email);
        mTextViewPassword =findViewById(R.id.password);
        mProgressBar=findViewById(R.id.progressBar);
        mButtonLogin =findViewById(R.id.loginButton);
        mTextViewTimer =findViewById(R.id.timerText);
        mCheckboxRemember=findViewById(R.id.checkBoxRememberMe);
        mPrefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        getPreferenceData();
        mPresenter = new LoginPresenter(this, new LoginModel());
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCredential();
            }
        });
    }
    @Override
    protected void onStart() // methods called after onCreate
    {
        super.onStart();
        mPresenter.checkIfAlreadyLogged();
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
    public void reportFailure()
    { Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_SHORT).show(); }

    @Override
    public void reportSuccess()
    { Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_SHORT).show(); }

    @Override
    public void startTimer() {
        mButtonLogin.setEnabled(false);
        mTextViewTimer.setVisibility(View.VISIBLE);
        mCountDownTimer=new CountDownTimer(TIME_TO_WAIT,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextViewTimer.setText(getString(R.string.await,millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                mTextViewTimer.setVisibility(View.INVISIBLE);
                mButtonLogin.setEnabled(true);
            }
        }.start();
    }

    @Override
    public void navigateToHome()
    {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mTextViewEmail =null;
        mPresenter=null;
        mButtonLogin =null;
        mProgressBar=null;
        mTextViewTimer =null;
        mCountDownTimer=null;
        mCheckboxRemember=null;
        mPrefs=null;
    }
    private void validateCredential()
    {
        mPresenter.validateCredentials(mTextViewEmail.getText().toString().trim(),
                mTextViewPassword.getText().toString().trim(),mCheckboxRemember.isChecked());
    }
    private void getPreferenceData()
    {
        SharedPreferences prefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(prefs.contains("user_email"))
        {
            String prefsEmail=prefs.getString("user_email","");
            mTextViewEmail.setText(prefsEmail.toString());
        }
        if(prefs.contains("user_password"))
        {
            String prefsPassword=prefs.getString("user_password","");
            mTextViewPassword.setText(prefsPassword.toString());
        }
        mCheckboxRemember.setChecked(true);
    }
}

