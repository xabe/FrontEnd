package com.xabe.formData.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

	private String message;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
