package com.pzm.pizzera.profile;


public class ProfilePresenter implements ProfileInteractor.OnValidate {
	private ProfileView profileView;
	private ProfileInteractor profileInteractor;

	public ProfilePresenter(ProfileView profileView, ProfileInteractor profileInteractor) {
		this.profileView = profileView;
		this.profileInteractor = profileInteractor;
	}


	public void validateCredentials(String name, String surname, String phone, String email, String salary){

		profileInteractor.validate(name, surname, phone, email, salary, this);
	}

	@Override
	public void onNameError(){
		if (profileView != null) {
			profileView.setNameError();
		}
	}

	@Override
	public void onSurnameError(){
		if (profileView != null) {
			profileView.setSurnameError();
		}
	}

	@Override
	public void onPhoneError(){
		if (profileView != null) {
			profileView.setPhoneError();
		}
	}

	@Override
	public void onEmailError(){
		if (profileView != null) {
			profileView.setEmailError();
		}
	}

	@Override
	public void onSalaryError(){
		if (profileView != null) {
			profileView.setNameError();
		}
	}

}
