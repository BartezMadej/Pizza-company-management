package com.pzm.pizzera.login;

public interface LoginView {
	void showProgress();

	void hideProgress();

	void setEmailError();

	void setPasswordError();

	void navigateToHome();

	void reportFailure();

	void reportSuccess();

	void startTimer();
}
