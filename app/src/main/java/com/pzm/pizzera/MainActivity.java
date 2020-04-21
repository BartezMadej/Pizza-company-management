package com.pzm.pizzera;

import android.content.Intent;
import android.os.Bundle;

import com.pzm.pizzera.register.RegisterActivity;


public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent myIntent = new Intent(this, RegisterActivity.class);
		startActivity(myIntent);
	}
}
