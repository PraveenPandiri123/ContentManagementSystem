package com.example.cms.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.UserAlreadyExistsByEmailException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandling extends RuntimeException {
	private ErrorStructure<String> errorStructure;
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistsByEmailException(
			UserAlreadyExistsByEmailException ex) {
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Alredy User with this email is exists");
	}

	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status, String message, String rootcause) {

		return new ResponseEntity<ErrorStructure<String>>(
				errorStructure.setStatus(status.value()).setMessage(message).setRootCause(rootcause), status);
	}

}
