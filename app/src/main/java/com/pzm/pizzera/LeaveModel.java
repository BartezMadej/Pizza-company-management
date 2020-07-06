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
public class LeaveModel {
	private String from;
	private String to;
	private String approved;
	private String userId;
	private String id;
}
