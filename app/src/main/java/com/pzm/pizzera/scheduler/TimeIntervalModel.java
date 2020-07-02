package com.pzm.pizzera.scheduler;

import com.google.firebase.database.annotations.Nullable;

import java.util.Map;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TimeIntervalModel {
	Map<String, String> times = new TreeMap<>();
}
