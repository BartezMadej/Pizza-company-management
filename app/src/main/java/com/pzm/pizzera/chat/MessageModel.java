package com.pzm.pizzera.chat;

import lombok.Data;

@Data
public class MessageModel {
	String sendBy;
	String message;
	String messageDate;
	String messageTime;
}
