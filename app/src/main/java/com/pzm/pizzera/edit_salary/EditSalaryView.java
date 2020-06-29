package com.pzm.pizzera.edit_salary;

public interface EditSalaryView {
	void setSalaryError();

	void setBonusError();

	void updateUserInfo(String name, String surname, String salary, String bonus, String photo);

	void switchView();
}
