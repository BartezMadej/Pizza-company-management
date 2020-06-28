package com.pzm.pizzera.edit_salary;

import com.pzm.pizzera.UserModel;

public class EditSalaryInteractor extends UserModel {
	interface OnValidate {

		void onNameError();

		void onSurnameError();

		void onSalaryError();
		void onBonusError();

	}

	public void validate(String name, String surname, String salary, String bonus, OnValidate listener) {
		String regexStr = "^[0-9]{9}$";

		regexStr = "^[1-9]+$";
		if (!salary.matches(regexStr)) {
			listener.onSalaryError();
			return;
		}

		if(!bonus.matches(regexStr)) {
			listener.onBonusError();
			return;
		}
		regexStr = "([A-Z][a-z][a-z]*)";
		if (!name.matches(regexStr)) {
			listener.onNameError();
			return;
		}
		if (!surname.matches(regexStr)) {
			listener.onSurnameError();
			return;
		}
	}
}
