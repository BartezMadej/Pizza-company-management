package com.pzm.pizzera.leaves;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.annotations.Nullable;
import com.pzm.pizzera.LeaveModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserLeaveModel {
	private LeaveModel leaveModel;
	private String userId;
	private String userName;
	private String userSurname;
}
