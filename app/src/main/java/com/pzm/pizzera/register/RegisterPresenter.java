package com.pzm.pizzera.register;

import android.util.Log;

import com.pzm.pizzera.UserModel;

public class RegisterPresenter implements RegisterInteractor.OnRegisterFinishedListener {
	private final RegisterView registerView;
	private final RegisterInteractor registerInteractor;

	RegisterPresenter(RegisterView registerView, RegisterInteractor registerInteractor) {
		this.registerView = registerView;
		this.registerInteractor = registerInteractor;
	}

	public void validateCredentials(UserModel user) {
		if (registerView != null) {
			registerView.showProgress();
		}
		registerInteractor.register(user, this);
	}


	@Override
	public void onEmailError() {
		if (registerView != null) {
			registerView.setEmailError();
			registerView.hideProgress();
		}
	}

	@Override
	public void onEmailExists() {
		if (registerView != null) {
			registerView.setEmailExistsError();
			registerView.hideProgress();
		}
	}

	@Override
	public void onNameError() {
		if (registerView != null) {
			registerView.setNameError();
			registerView.hideProgress();
		}
	}

	@Override
	public void onSurnameError() {
		if (registerView != null) {
			registerView.setSurnameError();
			registerView.hideProgress();
		}
	}

	@Override
	public void onPhoneNumberError() {
		if (registerView != null) {
			registerView.setPhoneNumberError();
			registerView.hideProgress();
		}
	}

	@Override
	public void onSuccess() {
		registerView.hideProgress();
		Log.d("RegisterPresenter", "onSuccess: User Created");
	}
}
