package com.pzm.pizzera.wholesale;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
	public String amount;
	public String product;

	public Order(){}
	@NonNull
	@Override
	public String toString() {
		return amount + "\t|\t" + product;
	}
}
