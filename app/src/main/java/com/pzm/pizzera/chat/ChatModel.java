package com.pzm.pizzera.chat;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ChatModel {
	private String lastMessage;
	private ArrayList<String> members=new ArrayList<>();
}
