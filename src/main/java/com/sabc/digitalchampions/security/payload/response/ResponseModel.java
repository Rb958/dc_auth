package com.sabc.digitalchampions.security.payload.response;


import com.sabc.digitalchampions.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class ResponseModel<T> {

	private final String message;
	private final int status;
	private final T data;

	public ResponseModel(String message, HttpStatus status) {
		this.message = message;
		this.status = status.value();
		this.data = null;
	}

	public ResponseModel(T data) {
		this.message = "Success";
		this.data = data;
		this.status = HttpStatus.OK.value();
	}

	public ResponseModel(AbstractException e) {
		this.message = e.getMessage();
		this.status = e.getCode();
		this.data = null;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public T getData() {
		return data;
	}
}
