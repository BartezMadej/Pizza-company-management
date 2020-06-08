package com.pzm.pizzera;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.annotations.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserModel {
	@Exclude
	private String id;
	@Nullable
	private String salary;
	private String name;
	private String surname;
	private UserRole role;
	private String phoneNumber;
	private String email;
	@Nullable
	private String photo;
}
