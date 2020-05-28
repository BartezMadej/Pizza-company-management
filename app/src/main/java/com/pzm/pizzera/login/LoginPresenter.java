package com.pzm.pizzera.login;


public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {
	private LoginView loginView;
	private LoginInteractor loginInteractor;

	LoginPresenter(LoginView loginView, LoginInteractor loginInteractor)  //constructor
	{
		this.loginView = loginView;
		this.loginInteractor = loginInteractor;
	}

	public void validateCredentials(String email, String password, boolean ifChecked) {    //login and validation
		if (!loginInteractor.validate(email, password, this))
			return;
		loginView.showProgress();
		loginInteractor.login(email, password, ifChecked, this);
	}

	public void checkIfAlreadyLogged() {
		loginInteractor.alreadyLogged(this);
	}

	@Override
	public void onEmailError() {
		if (loginView == null)
			return;
		loginView.setEmailError();
		loginView.hideProgress();
	}

	@Override
	public void onPasswordError() {
		if (loginView == null)
			return;
		loginView.setPasswordError();
		loginView.hideProgress();
	}

	@Override
	public void onSuccess() {
		if (loginView == null)
			return;
		loginView.reportSuccess();
		loginView.navigateToHome();
	}

	@Override
	public void onFailed() {
		if (loginView == null)
			return;
		loginView.hideProgress();
		loginView.reportFailure();
	}

	@Override
	public void onLogged() {
		if (loginView == null)
			return;
		loginView.navigateToHome();
	}

	@Override
	public void onLocked() {
		loginView.startTimer();
	}

	public void onDestroy() {
		loginView = null;
	}
}