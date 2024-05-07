package com.task.threeline.wallet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralResponse {

	private int status;

	private String message;
	
	private Object data;
	
	public GeneralResponse(int status, String message)
	{
		this.status = status;
		this.message = message;
	}

	public GeneralResponse(int status, String message, Object data)
	{
		this.status = status;
		this.message = message;
		this.data = data;
	}
}
