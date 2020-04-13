package com.pzm.pizzera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import lombok.Data;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
