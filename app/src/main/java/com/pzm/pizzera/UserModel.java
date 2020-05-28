package com.pzm.pizzera;

import androidx.annotation.NonNull;

import lombok.Data;

@Data
public class UserModel {
	private String id;
	private String salary;
	private String name;
	private String surname;
	private String role;
	private String phoneNumber;
	private String email;
	private String photo;
	@Override
	public @NonNull String toString()
	{
		return this.surname+" "+this.name;
	}
}
