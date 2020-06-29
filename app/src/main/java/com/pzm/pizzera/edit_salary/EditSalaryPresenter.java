package com.pzm.pizzera.edit_salary;

public class EditSalaryPresenter implements EditSalaryInteractor.OnValidate{
	private EditSalaryView profileView;
	private EditSalaryInteractor profileInteractor;

	public EditSalaryPresenter(EditSalaryView profileView, EditSalaryInteractor profileInteractor) {
		this.profileView = profileView;
		this.profileInteractor = profileInteractor;
	}


	public void validateCredentials(String salary, String bonus) {
		profileInteractor.validate(salary, bonus, this);
	}

	@Override
	public void onSalaryError() {
		if (profileView != null) {
			profileView.setSalaryError();
		}
	}

	@Override
	public void onBonusError() {
		if (profileView != null) {
			profileView.setBonusError();
		}
	}
}