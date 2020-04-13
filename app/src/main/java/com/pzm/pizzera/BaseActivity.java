package com.pzm.pizzera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

	private ProgressBar progressBar;

	public void showProgressBar() {
		if (progressBar != null) {
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	public void hideProgressBar() {
		if (progressBar != null) {
			progressBar.setVisibility(View.INVISIBLE);
		}
	}

	public void setProgressBar(int resId) {
		progressBar = findViewById(resId);
	}

	public String getUid() {
		return FirebaseAuth.getInstance().getCurrentUser().getUid();
	}

}
