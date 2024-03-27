package com.example.cms.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ErrorStructure<T> {
	private int Status;
	private String message;
	private T rootCause;

	public int getStatus() {
		return Status;
	}

	public ErrorStructure<T> setStatus(int status) {
		Status = status;
		return this;
	}

	public ErrorStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public ErrorStructure<T> setRootCause(T rootCause) {
		this.rootCause = rootCause;
		return this;
	}

}
