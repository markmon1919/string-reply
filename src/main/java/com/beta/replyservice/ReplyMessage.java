package com.beta.replyservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ReplyMessage {

	private String data;
	private String message;
	private Integer status;

	public ReplyMessage(String message) {
		String msgPart = message.substring(message.indexOf("-") + 1, message.length());
		if (Character.isDigit(msgPart.charAt(0))) {
			this.message = "Invalid input"; // no string provided
			this.status = 400;
		} 
		else if (Character.isDigit(message.charAt(0)) && message.contains("-")) {
			String rules = message.substring(0, message.indexOf("-"));
			String ruleValid = String.valueOf(rules.charAt(rules.length() - 1));
			if (!Character.isDigit(ruleValid.charAt(0))) {
				//invalid rule
				this.data = message;
			} else {
				//valid rule
				String msg = new String(msgPart);
				int[] ruleCode = new int[rules.length()];
				for (int i = 0; i < rules.length(); i++) {
					ruleCode[i] = rules.charAt(i) - '0';
				}
				for(int num : ruleCode) {
					if (num == 1) {
						msg = new StringBuffer(msg).reverse().toString();
					}
					else if (num == 2) {
						msg = MD5Convert.getMd5(msg);
					} else {
						msg = null;
						this.message = "Invalid input";
						this.status = 400;
						break;
					}
				}
				this.data = msg;
			}
		} else {
			this.message = message; // return all message if no rules
		}
	}

	@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
	public Integer getStatus() {
		return status;
	}

	@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
	public String getMessage() {
		return message;
	}

	@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
	public String getData() {
		return data;
	}

}