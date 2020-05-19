package com.pzm.pizzera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

	private ProgressBar progressBar;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_base, container, false);
		return view;
	}

	protected void showProgressBar() {
		if (progressBar != null) {
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	protected void hideProgressBar() {
		if (progressBar != null) {
			progressBar.setVisibility(View.INVISIBLE);
		}
	}

	public void setProgressBar(int resId) {
		progressBar = view.findViewById(resId);
	}

	public String getUid() {
		return FirebaseAuth.getInstance().getCurrentUser().getUid();
	}

}
