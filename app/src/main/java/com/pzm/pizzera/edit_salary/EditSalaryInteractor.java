package com.pzm.pizzera.edit_salary;

import com.pzm.pizzera.UserModel;

public class EditSalaryInteractor extends UserModel {
	interface OnValidate {
		void onSalaryError();
		void onBonusError();
	}

	public void validate(String salary, String bonus, OnValidate listener) {
		String regexStr = "^[1-9]+$";
		if (!salary.matches(regexStr)) {
			listener.onSalaryError();
			return;
		}

		if(!bonus.matches(regexStr)) {
			listener.onBonusError();
			return;
		}
	}
}
