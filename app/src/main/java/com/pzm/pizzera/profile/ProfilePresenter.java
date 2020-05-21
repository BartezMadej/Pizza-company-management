package com.pzm.pizzera.profile;

import android.text.TextUtils;
import android.util.Patterns;

public class ProfilePresenter extends ProfileInteractor implements ProfileInteractor.OnValidate {
	private ProfileInteractor profileModel;
	private ProfileInteractor profileInteractor;

	public ProfilePresenter(ProfileFragment profileFragment, ProfileInteractor profileInteractor) {
		super();
	}

	@Override
	public boolean validate(){
		String regexStr = "^[0-9]{9}$";
		if (!profileModel.getPhone().matches(regexStr)){
			return false;
		}
		regexStr = "^[1-9]+$";
		if (!this.getSalary().matches(regexStr)){
			return false;
		}
		regexStr = "([A-Z][a-z][a-z]*)";
		if (!this.getName().matches(regexStr) || !this.getSurname().matches(regexStr)){
			return false;
		}
		if (TextUtils.isEmpty(this.getEmail()) || !Patterns.EMAIL_ADDRESS.matcher(this.getEmail()).matches()){
			return false;
		}
		return true;
	}


}
