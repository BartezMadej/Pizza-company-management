package com.pzm.pizzera.register;

public interface RegisterView {
	void setEmailError();

	void setEmailExistsError();

	void setNameError();

	void setSurnameError();

	void setPhoneNumberError();

	void showProgress();

	void hideProgress();
}
